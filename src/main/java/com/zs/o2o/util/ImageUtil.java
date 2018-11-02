package com.zs.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
    private static final Random r = new Random();

    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        String realName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(
                            new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    public static String generateNormalThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        String realName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(
                            new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径涉及的目录
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirFile = new File(realFileParentPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(CommonsMultipartFile thumbnail) {
        String originalFilename = thumbnail.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，年月日时分秒+五位随机数
     *
     * @return
     */
    private static String getRandomFileName() {
        String nowTimeStr = sdf.format(new Date());
        int ranNum = r.nextInt(89999) + 10000;
        return nowTimeStr + ranNum;
    }

    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                Arrays.stream(files).forEach(e -> e.delete());
            }
            fileOrPath.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        // Thumbnails.of(new File("D:\\真摸鱼.gif")).size(200, 200)
        //         .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(
        //                 new File(basePath + "/watermark.jpg")), 0.25f)
        //         .outputQuality(0.8f).toFile("d:/zhenmoyu.gif");
        deleteFileOrPath("\\upload\\item\\shop\\16\\20181031033049686.jpg");
    }
}
