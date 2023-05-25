import com.google.common.base.CaseFormat;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author szy
 * @date 2022/8/15 9:29
 */
public class MyTest {
    @Test
    public void unescapeJava() throws IOException {
        String str = "\"group by cloudvedio_device.device_model\\n\" +\n" +
                "                \") t\\n\" +\n" +
                "                \"order by t.device_model_count desc\\n\" +\n" +
                "                \"limit 10\"";
        String out = "";
        //Writer out = new StringWriter();
        if (str != null) {
            int sz = str.length();
            StringBuilder unicode = new StringBuilder(4);
            boolean hadSlash = false;
            boolean inUnicode = false;

            for (int i = 0; i < sz; ++i) {
                char ch = str.charAt(i);
                if (inUnicode) {
                    unicode.append(ch);
                    if (unicode.length() == 4) {
                        try {
                            int nfe = Integer.parseInt(unicode.toString(), 16);
                            //out.write((char) nfe);
                            out += (char) nfe;
                            unicode.setLength(0);
                            inUnicode = false;
                            hadSlash = false;
                        } catch (NumberFormatException var9) {
                        }
                    }
                } else if (hadSlash) {
                    hadSlash = false;
                    switch (ch) {
                        case '\"':
                            //out.write(34);
                            out += 34;
                            break;
                        case '\'':
                            //out.write(39);
                            out += 39;
                            break;
                        case '\\':
                            //out.write(92);
                            out += 92;
                            break;
                        case 'b':
                            //out.write(8);
                            out += 8;
                            break;
                        case 'f':
                            //out.write(12);
                            out += 12;
                            break;
                        case 'n':
                            //out.write(10);
                            out += 10;
                            break;
                        case 'r':
                            //out.write(13);
                            out += 13;
                            break;
                        case 't':
                            //out.write(9);
                            out += 9;
                            break;
                        case 'u':
                            inUnicode = true;
                            break;
                        default:
                            //out.write(ch);
                            out += ch;
                    }
                } else if (ch == 92) {
                    hadSlash = true;
                } else {
                    //out.write(ch);
                    out += ch;
                }
            }

            if (hadSlash) {
                //out.write(92);
                out += 92;
            }

        }
        System.out.println(out.toString());
    }

    @Test
    public void mtets() {
        String s = "";
        StringBuilder s1 = new StringBuilder(s);
        StringBuilder s2 = new StringBuilder();
        //首先替换掉引号，但是若遇到反斜杠，则跳过
        int length = s1.length();
        int match = 0;
        for (int i = 0; i < length; i++) {
            if (match % 2 == 0 && s1.charAt(i) != '\"') {
                //s1.deleteCharAt(i);
            } else {
                switch (s1.charAt(i)) {
                    case '\"':
                        //s1.deleteCharAt(i);
                        match++;
                        break;
                    case '\\':
                        //s1.deleteCharAt(i);
                        switch (s1.charAt(i + 1)) {
                            case 'n':
                                //s1.setCharAt(i + 1, (char) 10);
                                s2.append((char) 10);
                                break;
                            case 't':
                                //s1.setCharAt(i + 1, (char) 9);
                                s2.append((char) 9);
                                break;
                        }
                        i++;
                        break;
                    default:
                        s2.append(s1.charAt(i));
                }
            }
        }
        System.out.println(s2);
    }

    @Test
    public void sdfas() {
        char c = (char) 10;
        StringBuilder s = new StringBuilder("abc");
        s.append((char) 10);
        s.append("d");
        System.out.println(s);
    }

    @Test
    public void sdaf() {
        StringBuilder sb = new StringBuilder("abcd, ");
        sb.replace(sb.length() - 2, sb.length(), " ");
        System.out.println(sb);

    }

    @Test
    public void hklkasdf() throws IOException {
        //新建工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //新建工作表
        XSSFSheet sheet = xssfWorkbook.createSheet("工作表1");
        //指定合并开始行、合并结束行 合并开始列、合并结束列
        CellRangeAddress dateCol1 = new CellRangeAddress(0, 2, 0, 0);
        CellRangeAddress nameCol2_1 = new CellRangeAddress(0, 0, 1, 4);
        CellRangeAddress nameCol2_2 = new CellRangeAddress(1, 1, 1, 4);
        //添加要合并地址到表格
        sheet.addMergedRegion(dateCol1);
        sheet.addMergedRegion(nameCol2_1);
        sheet.addMergedRegion(nameCol2_2);
        //创建行，指定起始行号，从0开始
        XSSFRow row = sheet.createRow(0);
        //创建单元格，指定起始列号，从0开始
        XSSFCell cell = row.createCell(0);
        //设置单元格内容
        cell.setCellValue("我是合并后的单元格");
        //创建样式对象
        CellStyle style = xssfWorkbook.createCellStyle();
        //设置样式对齐方式：水平\垂直居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //设定填充单色
        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设定背景颜色
        //style.setFillForegroundColor(IndexedColors.PINK.getIndex());
        //设定粗边框
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        //为指定单元格设定样式
        //cell.setCellStyle(style);
        //row.setRowStyle(style);
        FileOutputStream fileOutputStream = new FileOutputStream("e:\\MergeCellDemo.xlsx");
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void sdfasf() {
        String s = "select * from test where 1=1";
        s += "delete from t_sys_dict where category_id = '{0};'";
        System.out.println(s);
    }

    public enum Dimension {
        CITY, TOWN, NAME;
    }

    @Test
    public void name() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = dateFormat.parse("20221122");
            System.out.println(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
