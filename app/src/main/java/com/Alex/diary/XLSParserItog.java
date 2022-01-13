package com.Alex.diary;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XLSParserItog extends AppCompatActivity {

    private WebView webView;
    private String url;
    private String cookies;
    String destinationName = "MarksItog.xls";
    public List<List> Items;
    File file = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xls);
        checkDownloadPermission();
        DeleteOld(destinationName);
        cookies = getIntent().getStringExtra("gfjxsasd");
        url = getIntent().getStringExtra("djhjgfj");
        this.registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        Log.d("Web", url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("MPskovedu");
        request.setDescription("Загрузка...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, destinationName);
        request.addRequestHeader("Cookie", cookies);
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);

        Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();


    }
    private void checkDownloadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Нет, разрешения на загрузку файлов!", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Нет, разрешения на чтение файлов!", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }
    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
                Log.d("download", "end");
                FindFile(downloadId);
            }
        }
    };
    private void FindFile(final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            @SuppressLint("Range") String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                openDownloadedExcel(new File(Uri.parse(downloadLocalUri).getPath()));
            }
        }
        cursor.close();
    }
    // delete the original file
    private void DeleteOld(String inputFile) {
        file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + inputFile);
        if (file!=null) file.delete();
        //Log.d("Moving", String.valueOf(succes));
    }
    private void openDownloadedExcel(File file){
        if(file!=null) {
                Items = ExcelUtilsItog.readExcel(file);
                ListView listView = findViewById(R.id.list);
                ProgramAdapterItog adapter = null;
                if (Items!=null) adapter = new ProgramAdapterItog(this, Items.get(0), Items.get(1), Items.get(2), Items.get(3), Items.get(4));
                listView.setAdapter(adapter);
                Log.d("reader", Items.toString());
        }
    }
}
class ExcelUtilsItog {

    /**
     * Read Excel File
     * @param file
     * @throws FileNotFoundException
     * @return
     */
    public static List<List> readExcel(File file){
        if(file == null) {
            Log.e("NullFile","read Excel Error, file is empty");
            return new ArrayList<List>();
        }
        List<List> All = new ArrayList<List>();
        List<String> Items = new ArrayList<String>();
        List<String> First = new ArrayList<String>();
        List<String> Second = new ArrayList<String>();
        List<String> Third= new ArrayList<String>();
        List<String> Fourth = new ArrayList<String>();
        try {

            FileInputStream infile = new FileInputStream(file);
            Workbook workbook = new HSSFWorkbook(infile);
            Sheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (int r = 3; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = getCellAsString(row, 1, formulaEvaluator);
                if(!value.equals(""))Items.add(value);

            }for (int r = 3; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = getCellAsString(row, 2, formulaEvaluator);
                First.add(value);

            }for (int r = 3; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = getCellAsString(row, 3, formulaEvaluator);
                Second.add(value);

            }for (int r = 3; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = getCellAsString(row, 4, formulaEvaluator);
                Third.add(value);

            }for (int r = 3; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = getCellAsString(row, 5, formulaEvaluator);
                Fourth.add(value);

            }
            All.add(Items);
            All.add(First);
            All.add(Second);
            All.add(Third);
            All.add(Fourth);
            Log.d("reader", All.toString());

            return All;
        }catch (Exception e) {
            /* proper exception handling to be here */
            Log.e("reader", e.toString());
        }
        return All;
    }

    /**
     * Read the contents of each line in the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private static String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    int numericValue = (int) cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
                    break;
            }
        } catch (NullPointerException e) {
            /* proper error handling should be here */
            //Log.e("reader", e.toString());
        }
        return value;
    }

    /**
     * Simply determine whether an Excel file is an Excel file based on the type suffix name
     * @param file file
     * @return Is Excel File
     */
    public static boolean checkIfExcelFile(File file){
        if(file == null) {
            return false;
        }
        String name = file.getName();
        //"."Escape characters are required
        String[] list = name.split("\\.");
        //Less than two elements after partitioning indicate that the type name cannot be obtained
        if(list.length < 2) {
            return false;
        }
        String  typeName = list[list.length - 1];
        //Satisfies xls or xlsx to be able to
        return "xls".equals(typeName) || "xlsx".equals(typeName);
    }
}