package com.commit451.youtubeextractor;

import com.commit451.youtubeextractor.extraction.ExtractionException;
import com.commit451.youtubeextractor.extraction.OkHttpDownloader;
import com.commit451.youtubeextractor.extraction.StreamUrlIdHandler;
import com.commit451.youtubeextractor.extraction.YoutubeStreamExtractor;
import com.commit451.youtubeextractor.extraction.YoutubeStreamUrlIdHandler;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Converts the bodies for the YouTubes
 */
class YouTubeBodyConverter implements Converter<ResponseBody, YouTubeExtractionResult> {

    private String mPageUrl;
    private OkHttpDownloader mOkHttpDownloader;

    public YouTubeBodyConverter(OkHttpDownloader downloader, String pageUrl) {
        mPageUrl = pageUrl;
        mOkHttpDownloader = downloader;
    }

    @Override
    public YouTubeExtractionResult convert(ResponseBody value) throws IOException {
        StreamUrlIdHandler urlIdHandler = new YoutubeStreamUrlIdHandler();

        String html = value.string();
        try {
            YoutubeStreamExtractor extractor = new YoutubeStreamExtractor(urlIdHandler, mPageUrl, mOkHttpDownloader, 0);
            YouTubeExtractionResult result = new YouTubeExtractionResult();
            result.mAgeLimit = extractor.getAgeLimit();
            result.mAudioStreams = extractor.getAudioStreams();
            result.mAverageRating = extractor.getAverageRating();
            result.mDescription = extractor.getDescription();
            result.mDislikeCount = extractor.getDislikeCount();
            result.mLength = extractor.getLength();
            result.mLikeCount = extractor.getLikeCount();
            result.mPageUrl = extractor.getPageUrl();
            result.mUploader = extractor.getUploader();
            result.mTitle = extractor.getTitle();
        } catch (ExtractionException e) {
            throw new IOException("Failed to extract");
        }
        return null;
    }
}
