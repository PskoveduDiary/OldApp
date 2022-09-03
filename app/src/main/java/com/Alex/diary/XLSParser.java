package com.Alex.diary;

import static com.Alex.diary.ui.all_marks.MarksAllFragment.Load;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Alex.diary.ui.all_marks.MarksAllFragment;
import com.Alex.diary.ui.error.ErrorFragment;
import com.Alex.diary.ui.full_info_marks.MarksInfoFragment;

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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XLSParser extends AppCompatActivity {

    private WebView webView;
    private String url;
    private String cookies;
    public static Context contextt;
    public File downloaded;
    public static FragmentManager fm;
    String destinationName = "MarksQuarter.xls";
    public static List<List> Items;
    File file = null;
    public static boolean IsMarksInfo = false;
    public static int PositionSelected;
    static MarksInfoFragment marksInfoFragment;
    static MarksAllFragment marksAllFragment;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xls);
        //checkDownloadPermission();
        contextt = getApplicationContext();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        marksInfoFragment = new MarksInfoFragment();
        marksAllFragment = new MarksAllFragment();
        ft.replace(R.id.fragmentXLS, marksAllFragment);
        ft.commit();
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
    /**
     * Check if the user has already given permission to download files. If not, ask for permission
     */
    /*private void checkDownloadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Нет, разрешения на загрузку файлов!", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }*/
    //
    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                Log.d("download", "end");
                FindFile(downloadId);
            }
        }
    };
    // The above code is find the downloaded file and then opening it.
    private void FindFile(final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            @SuppressLint("Range") String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                downloaded = new File(Uri.parse(downloadLocalUri).getPath());
                openDownloadedExcel(downloaded);
            }
        }
        cursor.close();
    }
    // delete the original file
    private void DeleteOld(String inputFile) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + inputFile);
            if (file != null) file.delete();
        }
        //Log.d("Moving", String.valueOf(succes));
    }
    /**
     * Reads the excel file and returns a list of items
     *
     * @param file The file that you want to read.
     */
    private void openDownloadedExcel(File file) {
        if(file!=null) {
            try {
                Items = ExcelUtils.readExcel(file);
                Load();
                Log.d("reader", Items.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * This function opens the marks info fragment
     *
     * @param pos The position of the item in the list that you want to open.
     */
    public static void OpenInfo(int pos){
        IsMarksInfo = true;
        PositionSelected = pos;
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentXLS, marksInfoFragment);
        ft.commit();
        FragmentTransaction ft2 = fm.beginTransaction();
        ft2.hide(marksAllFragment);
        ft2.commit();
    }
    /**
     * This function is called when the user presses the back button.
     * If the user is in the marksInfoFragment, then the user is taken back to the marksAllFragment.
     * If the user is in the marksAllFragment, then the user is taken back to the main menu
     */
    @Override
    public void onBackPressed() {

        if (!IsMarksInfo) {
            super.onBackPressed();

        } else {

            IsMarksInfo = false;
            FragmentTransaction ft2 = fm.beginTransaction();
            ft2.show(marksAllFragment);
            ft2.commit();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(marksInfoFragment);
            ft.commit();
        }

    }
    /**
     * This function is used to display an error message to the user
     *
     * @param ErrorMessage The error message to display.
     */
    @Nullable
    public static void PutErrorCode(String ErrorMessage){
        if (ErrorMessage == null) ErrorMessage = "None";
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentXLS, new ErrorFragment());
        ft.commit();
        ErrorFragment.Error(ErrorMessage);
    }
}
 class ExcelUtils {

    /**
     * Reads the contents of the Excel file and returns a list of lists
     *
     * @param file The file to be read.
     * @return A list of lists.
     */
    public static List<List> readExcel(File file) throws IOException {
        if(file == null) {
            Log.e("NullFile","read Excel Error, file is empty");
            return new ArrayList<List>();
        }
        List<List> All = new ArrayList<List>();
        List<String> Items = new ArrayList<String>();
        List<String> Vypiska = new ArrayList<String>();
        List<Double> Itog = new ArrayList<Double>();
        List<String> NoShow = new ArrayList<String>();
        List<String> Pass = new ArrayList<String>();
        List<String> Disease = new ArrayList<String>();
        List<String> Lateness = new ArrayList<String>();
        try {
            FileInputStream infile = new FileInputStream(file);
            Workbook workbook = new HSSFWorkbook(infile);
            Sheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                    //Convert the contents of each grid to a string
                    String value = getCellAsString(row, 1, formulaEvaluator);
                Items.add(value);

            }
            for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = getCellAsString(row, 2, formulaEvaluator);
                Vypiska.add(value);

            }for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                Double value = getCellAsDouble(row, 3, formulaEvaluator);
                Itog.add(value);

            }for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = ""+getCellAsInt(row, 4, formulaEvaluator);
                NoShow.add(value);

            }for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = ""+getCellAsInt(row, 5, formulaEvaluator);
                Pass.add(value);

            }for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = ""+getCellAsInt(row, 6, formulaEvaluator);
                Disease.add(value);

            }for (int r = 4; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //Read one line at a time
                //Convert the contents of each grid to a string
                String value = ""+getCellAsInt(row, 7, formulaEvaluator);
                Lateness.add(value);

            }
            All.add(Items);
            All.add(Vypiska);
            All.add(Itog);
            All.add(NoShow);
            All.add(Pass);
            All.add(Disease);
            All.add(Lateness);
            Log.d("reader", Items.toString());

            return All;
        } catch (Exception e) {
            /* proper exception handling to be here */
            Log.e("reader", e.toString());
            if(e.equals(java.io.FileNotFoundException.class)) XLSParser.PutErrorCode("FILE_NOT_FOUND_PERMISSIONS?");
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
                    float numericValue = (float) cellValue.getNumberValue();
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
     static int getCellAsInt(Row row, int c, FormulaEvaluator formulaEvaluator){
         int value = 0;
         try {
             Cell cell = row.getCell(c);
             CellValue cellValue = formulaEvaluator.evaluate(cell);
             switch (cellValue.getCellType()) {
                 case Cell.CELL_TYPE_NUMERIC:
                     int numericValue = (int) cellValue.getNumberValue();
                         value = numericValue;
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
     static double getCellAsDouble(Row row, int c, FormulaEvaluator formulaEvaluator){
         double value = 0.0;
         try {
             Cell cell = row.getCell(c);
             CellValue cellValue = formulaEvaluator.evaluate(cell);
             switch (cellValue.getCellType()) {
                 case Cell.CELL_TYPE_NUMERIC:
                     double numericValue = (double) cellValue.getNumberValue();
                     value = numericValue;
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