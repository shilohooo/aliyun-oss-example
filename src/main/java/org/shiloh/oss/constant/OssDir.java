package org.shiloh.oss.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.constant.DatePatternConstants;
import org.shiloh.constant.SymbolConstants;

import java.util.Date;
import java.util.UUID;

/**
 * OSS 目录名称枚举
 *
 * @author shiloh
 * @date 2024/10/23 17:00
 */
@Getter
@RequiredArgsConstructor
public enum OssDir {
    TEST("test", "测试目录"),
    FACE("face", "人脸照片");

    /**
     * 目录名称
     */
    private final String dir;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 创建文件对象名称
     *
     * @param filename 文件名
     * @return 文件对象名称
     * @author shiloh
     * @date 2024/10/23 17:25
     */
    public String createObjectName(String filename) {
        // 目录按日期分
        final String dateStr = DateFormatUtils.format(new Date(), DatePatternConstants.YYYYMMDD);
        // 文件名加上 UUID 防止重名覆盖
        final String uuid = UUID.randomUUID().toString().replaceAll(SymbolConstants.DASH, SymbolConstants.EMPTY);
        final String baseName = FilenameUtils.getBaseName(filename);
        final String extension = FilenameUtils.getExtension(filename);
        final String randomFilename = baseName + SymbolConstants.DASH + uuid + SymbolConstants.DOT + extension;
        return this.getDir() + SymbolConstants.SLASH + dateStr + SymbolConstants.SLASH + randomFilename;
    }
}
