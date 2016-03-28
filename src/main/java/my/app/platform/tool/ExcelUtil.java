package my.app.platform.tool;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 夏之阳
 * 创建时间：2016-03-20 14:55
 * 创建说明：Excel工具类
 */

public class ExcelUtil {
    /**
     * 读文件（后缀为xlsx）
     * @param path 文件路径
     * @return 字符串数组类型的文件内容
     * @throws IOException
     */
    public static List<List<String>> readXlsx(String path) throws IOException {
        List<List<String>> nameList = new ArrayList<List<String>>();

        InputStream inputStream = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        for(int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++){
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if(xssfSheet == null){
                continue;
            }
            for(int i = 1; i < xssfSheet.getLastRowNum(); i++){
                XSSFRow xssfRow = xssfSheet.getRow(i);

                int minCol = xssfRow.getFirstCellNum();
                int maxCol = xssfRow.getLastCellNum();

                List<String> row = new ArrayList<String>();
                for(int j = minCol; j < maxCol; j++){
                    XSSFCell xssfCell = xssfRow.getCell(j);
                    if(xssfCell == null){
                        continue;
                    }
                    row.add(getCellVal(xssfCell));
                }
                nameList.add(row);
            }
        }

        return nameList;
    }

    /**
     * 读文件（后缀为xls）
     * @param path 文件路径
     * @return 字符串数组类型的文件内容
     * @throws IOException
     */
    public static List<List<String>> readXls(String path) throws IOException {
        List<List<String>> nameList = new ArrayList<List<String>>();

        InputStream inputStream = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);

        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null){
                continue;
            }
            for(int i = 1; i < hssfSheet.getLastRowNum(); i++){
                HSSFRow hssfRow = hssfSheet.getRow(i);

                int minCol = hssfRow.getFirstCellNum();
                int maxCol = hssfRow.getLastCellNum();

                List<String> row = new ArrayList<String>();
                for(int j = minCol; j < maxCol; j++){
                    HSSFCell hssfCell = hssfRow.getCell(j);
                    if(hssfCell == null){
                        continue;
                    }
                    row.add(getCellVal(hssfCell));
                }
                nameList.add(row);
            }
        }

        return nameList;
    }

    /**
     * 将单元格格式转为String类型
     * @param cell 单元格
     * @return 字符串内容
     */
    private static String getCellVal(XSSFCell cell){
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    /**
     * 将单元格格式转为String类型
     * @param cell 单元格
     * @return 字符串内容
     */
    private static String getCellVal(HSSFCell cell){
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }
}
