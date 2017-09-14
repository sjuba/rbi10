/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import reporter.xml.SEnumFontAlign;

/**
 *
 * @author Alphalapz
 */
public abstract class SGeneratorExcelUtils {

    public static short getFontBoldCode(boolean isBold) {
        return isBold ? Font.BOLDWEIGHT_BOLD : Font.BOLDWEIGHT_NORMAL;
    }
    
    public static boolean getFontCursive(boolean isCursive) {
        return isCursive;
    }

    public static HorizontalAlignment getAlignCell(SEnumFontAlign align) {
        switch (align) {
            case CENTER:
                return HorizontalAlignment.CENTER;
            case LEFT:
                return HorizontalAlignment.LEFT;
            case RIGHT:
                return HorizontalAlignment.RIGHT;
            default:
                return HorizontalAlignment.JUSTIFY;
        }
    }
}
