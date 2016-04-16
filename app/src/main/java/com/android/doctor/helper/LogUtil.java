/*
 *  Copyright (c) 2015 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */package com.android.doctor.helper;

import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

import com.android.doctor.app.AppConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * 日志打印工具类
 */
public class LogUtil {

    private static boolean isPrint = true;
    private static boolean isDebug = false;

    public static final String TAG = "Restaurant_Log";
    public static final String MSG = "log msg is null.";

    private static String state = Environment.getExternalStorageState();

    private static List<String> logList;

    static {
        //isPrint = ECApplication.getInstance().getLoggingSwitch();
    }

    public static void v(String tag, String msg) {
        print(Log.VERBOSE, tag, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String tag, String msg) {
        print(Log.DEBUG, tag, msg);
        print(isDebug, msg);
        //appendByBufferedWriter(msg);
        //addToLog(tag, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String tag, String msg) {
        print(Log.INFO, tag, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String tag, String msg) {
        print(Log.WARN, tag, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String tag, String msg) {
        print(Log.ERROR, tag, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    private static void print(int mode, final String tag, String msg) {
        if (!isPrint) {
            return;
        }
        if (msg == null) {
            Log.e(tag, MSG);
            return;
        }
        switch (mode) {
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            default:
                Log.d(tag, msg);
                break;
        }
    }

    private static void print(boolean flag, String msg) {
        if (flag && logList != null) {
            logList.add(msg);
        }
    }

    public static void setState(boolean flag) {
        if (flag) {
            if (logList == null) {
                logList = new ArrayList<String>();
            } else {
                logList.clear();
            }
        } else {
            if (logList != null) {
                logList.clear();
                logList = null;
            }
        }

        isDebug = flag;
    }


    public static void addToLog(String tag, String message) {
        Time t = new Time("GMT+8");
        t.setToNow();
        int date = t.year * 10000 + t.month * 100 + t.monthDay;
        String log = "TIME: " + date + " "+ GregorianCalendar.HOUR_OF_DAY +":"+GregorianCalendar.MINUTE +
                ":"+GregorianCalendar.SECOND +"."+GregorianCalendar.MILLISECOND + "    PID: "+
                android.os.Process.myPid()+ "    TID: "+android.os.Process.myTid()+
                "    TAG: " + tag + "    " + message;

        File dir = null; //new File(AppConfig.LOG_DIR);
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if(!dir.exists()) {
                Log.d("Dir created ", "Dir created ");
                dir.mkdirs();
            }
            File logFile = null; //new File(AppConfig.LOG_DIR, "log.txt");
            if (!logFile.exists())  {
                try  {
                    Log.d("File created ", "File created ");
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //BufferedWriter for performance, true to set append to file flag
               // BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
                buf.write(log + "\r\n");
                //buf.append(message);
                buf.newLine();
                buf.flush();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void appendByBufferedWriter(String content) {
        if (!isPrint) {
            return;
        }
        BufferedWriter bw = null;
        try {
            Time t = new Time("GMT+8");
            t.setToNow();
            int date = t.year * 10000 + t.month * 100 + t.monthDay;
            int time = t.hour * 10000 + t.minute * 100 + t.second;
            String fileName = "log_" + date + "_" + time + ".txt";
            File file = null; //new File(AppConfig.LOG_DIR, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            bw.write(content);
            bw.flush();
            bw.close();
            //LogUtil.d("[FileAccessor - appendContent] append finished.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
                bw = null;
            }
        }
    }

    public static void printFile(InputStream is, String path) {
        FileOutputStream fos = null;
        byte[] temp = null;
        try {
            if (isPrint) {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                temp = new byte[1024];
                int i = 0;
                while ((i = is.read(temp)) > -1) {
                    if (i < temp.length) {
                        byte[] b = new byte[i];
                        System.arraycopy(temp, 0, b, 0, b.length);
                        fos.write(b);
                    } else {
                        fos.write(temp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
            temp = null;
        }
    }

    public static void printErrorStackTrace(Exception ex) {
        File f = new File("/sdcard/Hisun/error.log");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            ex.printStackTrace(new PrintStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  String getLogUtilsTag(Class<? extends Object> clazz) {
        return LogUtil.TAG + "." + clazz.getSimpleName();
    }

    public static void initLog() {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File logDirectory = null;// new File(AppConfig.LOG_DIR);
            File logFile = new File( logDirectory, "logcat_" + System.currentTimeMillis() + ".txt" );
            // create log folder
            if ( !logDirectory.exists() ) {
                logDirectory.mkdir();
            }
            // clear the previous logcat and then write the new one to the file
            try {
                String[] cmd = new String[] {"logcat", "-f", logFile.toString(), "-v", "time"};
                Process process = Runtime.getRuntime().exec( "logcat -c");
                process = Runtime.getRuntime().exec(cmd);
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

}
