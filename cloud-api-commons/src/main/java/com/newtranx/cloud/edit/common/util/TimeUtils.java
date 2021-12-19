package com.newtranx.cloud.edit.common.util;


import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * 描述:日期操作公共类
 *
 * @version V1.0
 */
public class TimeUtils {

    private static final Locale DEFAUTL_LOCALE = Locale.CHINA;

    public static ThreadLocal<DateFormat> TIME_FORMATE = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", DEFAUTL_LOCALE);
        }
    };
    public static ThreadLocal<DateFormat> DATE_FORMATE = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", DEFAUTL_LOCALE);
        }
    };
    
    public static ThreadLocal<DateFormat> YMD_FORMATE_PRC = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日", DEFAUTL_LOCALE);
        }
    };
    public static ThreadLocal<DateFormat> MD_FORMATE_PRC = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日", DEFAUTL_LOCALE);
        }
    };
    public static ThreadLocal<DateFormat> CURRENT_DATE_STRING = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyMMddHHmmSSS", DEFAUTL_LOCALE);
        }
    };
    public static ThreadLocal<DateFormat> YMDHMS_DATE_STRING = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss", DEFAUTL_LOCALE);
        }
    };
    public static ThreadLocal<DateFormat> YMD_DATE_STRING = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", DEFAUTL_LOCALE);
        }
    };

//    public static  SimpleDateFormat TIME_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", DEFAUTL_LOCALE);
//    public static final SimpleDateFormat DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd", DEFAUTL_LOCALE);
//    public static final SimpleDateFormat YMD_FORMATE_PRC = new SimpleDateFormat("yyyy年MM月dd日", DEFAUTL_LOCALE);
//    public static final SimpleDateFormat MD_FORMATE_PRC = new SimpleDateFormat("MM月dd日", DEFAUTL_LOCALE);
//    public static final SimpleDateFormat CURRENT_DATE_STRING = new SimpleDateFormat("yyMMddHHmmSSS", DEFAUTL_LOCALE);
    private static Date sysDate = new Date();

    /**
     * 描述:验证日期是否为空
     * <p>创建人：jrzhangwei , 2015年2月5日 下午5:53:55</p>
     *
     * @param compareDate
     * @return
     */
    public static  boolean checkIsNotEmpty(Date compareDate) {
        try {
      //  	TIME_FORMATE=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss", DEFAUTL_LOCALE);
            if (compareDate == null){
                compareDate = TIME_FORMATE.get().parse("1970-01-01 00:00:00");
            }
            Date emptyDate = TIME_FORMATE.get().parse("1970-01-01 00:00:00");
            if (compareDate.after(emptyDate)) {
                return true;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;
    }

    /**
     * 描述:设置业务日期
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:49:04</p>
     *
     * @param bizDate
     */
    public static void setSysDate(Date bizDate) {
        if (bizDate != null) {
            sysDate = bizDate;
        } else {
            sysDate = new Date();
        }
    }

    /**
     * 描述:计算日期累加多少天之后的日期 (输入负数，就是减少天)
     * <p>创建人：jrzhangwei , 2015年1月24日 上午8:58:42</p>
     *
     * @param inputDate 需要累加的日期
     * @param dayCount  累加的天数
     * @return
     */
    public static Date getDateIncDayCount(Date inputDate, int dayCount) {
        Calendar c = Calendar.getInstance();
        c.setTime(inputDate);
        c.add(Calendar.DAY_OF_YEAR, dayCount);
        return c.getTime();
    }
    
    /**
     * 描述:计算日期累加多少年之后的日期 (输入负数，就是减少天)
     * <p>创建人：jrzhangwei , 2015年1月24日 上午8:58:42</p>
     *
     * @param inputDate 需要累加的日期
     * @param yearCount  累加的年数
     * @return
     */
    public static Date getDateIncYearCount(Date inputDate, int yearCount) {
        Calendar c = Calendar.getInstance();
        c.setTime(inputDate);
        c.add(Calendar.YEAR, yearCount);
        return c.getTime();
    }
    
    /**
     * 描述:日期格式化(YMD-PRC)
     * <p>创建人：jrzhangwei , 2015年1月24日 上午8:58:42</p>
     *
     * @param date 需要格式化的日期
     * @return
     */
    public static String ymdFormatePRC(Date date){
    	return YMD_FORMATE_PRC.get().format(date);
    }
    
    /**
     * 描述:日期格式化(MD-PRC)
     * <p>创建人：jrzhangwei , 2015年1月24日 上午8:58:42</p>
     *
     * @param date 需要格式化的日期
     * @return
     */
    public static String mdFormatePRC(Date date){
    	return MD_FORMATE_PRC.get().format(date);
    }
    public static String ymdHMSFormatePRC(Date date){
        return YMDHMS_DATE_STRING.get().format(date);
    }

    /**
     * 描述:计算两个日期之间相差的天数
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:00:39</p>
     *
     * @param endDate   结束日期
     * @param startDate 开始日期
     * @return
     */
    public static long calDaysBetweenDate(Date endDate, Date startDate) {
        long days = 0;
        try {
            startDate = formatTime2Date(startDate);
            endDate = formatTime2Date(endDate);
            days = (endDate.getTime() - startDate.getTime()) / 60 / 60 / 1000 / 24;
        } catch (Exception e) {
            throw new RuntimeException("get failed", e);
        }
        return days;
    }

    /**
     * 描述:格式化当前日期（去掉时间信息）
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:01:33</p>
     *
     * @param date 需要格式化的日期
     * @return
     */
    public static Date formatTime2Date(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 描述:格式化当前日期(yyyy-MM-dd 23:59:59)
     *
     * @param date
     * @return
     */
    public static Date formatTimeEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 描述:获取日期中的天
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:01:48</p>
     *
     * @param date 指定日期
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 描述:获取当前系统日期
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:03:28</p>
     *
     * @return
     */
    public static Date getSysDate() {
        return sysDate;
    }

    /**
     * 描述:获取当前日期（时分秒都为0）
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:03:28</p>
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * 描述:获取指定时间月份的指定日期（时分秒都为0）
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:03:28</p>
     *
     * @return
     */
    public static Date getDateCustomMonth(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * 描述:获取当前月的指定日期（时分秒都为0）
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:03:28</p>
     *
     * @return
     */
    public static Date getDateCurrentMonth(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 描述:获取当前时间（包括时分秒）
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:03:28</p>
     *
     * @return
     */
    public static Date getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.getTime();
    }

    /**
     * 描述:判断date1和date2是否年和月相,如果相同就返回date2，否则返回date1
     * <p>创建人：jrzhangwei , 2015年1月24日 上午9:03:51</p>
     *
     * @param date1 需要比较的日期
     * @param date2 需要比较的日期
     * @return
     */
    public static Date isEqualsYM(Date date1, Date date2) {
        Calendar date1Temp = Calendar.getInstance();
        Calendar date2Temp = Calendar.getInstance();
        date1Temp.setTime(date1);
        date2Temp.setTime(date2);
        if ((date1Temp.get(Calendar.YEAR) == date2Temp.get(Calendar.YEAR)) && date1Temp.get(Calendar.MONTH) == date2Temp.get(Calendar.MONTH)) {
            date1 = date2;
        }
        return date1;
    }

    /**
     * 描述:根据指定的还款日对日期进行修约
     * <p>创建人：jrzhangwei , 2015年1月24日 上午8:37:18</p>
     *
     * @param result 需要修约的日期
     * @param dueDay 指定的还款日
     * @return
     */
    private static Date adjustByDueDay(Date result, int dueDay) {
        Calendar newResult = Calendar.getInstance();
        newResult.setTime(result);
        if (dueDay > 28) {
            int curDueDay = newResult.get(Calendar.DAY_OF_MONTH);
            if (curDueDay != dueDay) {
                int max = newResult.getActualMaximum(Calendar.DAY_OF_MONTH);
                curDueDay = dueDay;
                if (max < dueDay) {
                    curDueDay = max;
                }
                newResult.set(Calendar.DAY_OF_MONTH, curDueDay);
            }
        }
        return newResult.getTime();
    }

    /**
     * 描述:根据起始日期计算下期还款日期
     * <p>创建人：jrzhangwei , 2015年1月23日 下午9:27:15</p>
     *
     * @param startDate      起始日期
     * @param frequency      还款频率单位
     * @param frequencyRange 还款频率步长
     * @param dueDay         指定还款日
     * @return
     */
    private static Date getDateByPaymentFreq(Date startDate, String frequency, int frequencyRange, int dueDay) {
        if (startDate != null) {
            startDate = formatTime2Date(startDate);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        if (Frequency.PAY_MONTHLY.getCodeInDb().equals(frequency)) {
            cal.add(Calendar.MONTH, frequencyRange);
            return adjustByDueDay(cal.getTime(), dueDay);
        } else if (Frequency.PAY_QUARTERLY.getCodeInDb().equals(frequency)) {
            cal.add(Calendar.MONTH, 3);
            return adjustByDueDay(cal.getTime(), dueDay);
        } else if (Frequency.PAY_YEARLY_HALF.getCodeInDb().equals(frequency)) {
            cal.add(Calendar.MONTH, 6);
            return adjustByDueDay(cal.getTime(), dueDay);
        } else if (Frequency.PAY_WEEKLY.getCodeInDb().equals(frequency)) {
            cal.add(Calendar.DATE, frequencyRange * 7);
            return cal.getTime();
        } else if (Frequency.PAY_WEEKLY_2.getCodeInDb().equals(frequency)) {
            cal.add(Calendar.DATE, frequencyRange * 14);
            return cal.getTime();
        } else if (Frequency.PAY_YEARLY.getCodeInDb().equals(frequency)) {
            cal.add(Calendar.YEAR, frequencyRange);
            return adjustByDueDay(cal.getTime(), dueDay);
        } else {
            throw new RuntimeException("没有找到的日期频率为【" + frequency + "】的还款日！");
        }
    }

    /**
     * 描述:根据起始日期计算出对年对月日期
     * <p>创建人：jrzhangwei , 2015年1月23日 下午9:06:02</p>
     *
     * @param startDate 起始日期
     * @param months    增加的月数
     * @return
     */
    public static Date getDateByMonths(Date startDate, int months) {
        int dueDay = TimeUtils.getDay(startDate);
        return TimeUtils.getDateByPaymentFreq(startDate, Frequency.PAY_MONTHLY.getCodeInDb(), months, dueDay);
    }

    /**
     * 描述:还款频率公共类
     * <p>创建人：jrzhangwei 创建日期：2015年1月24日 </p>
     *
     * @version V1.0
     */
    public enum Frequency {
        PAY_WEEKLY, PAY_WEEKLY_2, PAY_MONTHLY, PAY_QUARTERLY, PAY_YEARLY_HALF, PAY_YEARLY, PAY_ALL_ONCE;

        public String getCodeInDb() {
            switch (values()[ordinal()]) {
                case PAY_WEEKLY:
                    return "W";
                case PAY_WEEKLY_2:
                    return "2W";
                case PAY_MONTHLY:
                    return "M";
                case PAY_QUARTERLY:
                    return "Q";
                case PAY_YEARLY_HALF:
                    return "HY";
                case PAY_YEARLY:
                    return "Y";
                case PAY_ALL_ONCE:
                    return "A";
            }
            throw new RuntimeException("not found enum");
        }

        public static Frequency getEnum(String frequency) {
            if ("W".equals(frequency)) {
                return PAY_WEEKLY;
            }
            if ("2W".equals(frequency)) {
                return PAY_WEEKLY_2;
            }
            if ("M".equals(frequency)) {
                return PAY_MONTHLY;
            }
            if ("Q".equals(frequency)) {
                return PAY_QUARTERLY;
            }
            if ("HF".equals(frequency)) {
                return PAY_YEARLY_HALF;
            }
            if ("Y".equals(frequency)) {
                return PAY_YEARLY;
            }
            if ("A".equals(frequency)) {
                return PAY_ALL_ONCE;
            }
            throw new RuntimeException("not found enum");
        }
    }

    /**
     * 根据date转化成string yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatString(Date date) {
        String formatStr = TIME_FORMATE.get().format(date);
        return formatStr;
    }
    /**
     * 根据date转化成string yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String formatStringNoSpace(Date date) {
		String string=CURRENT_DATE_STRING.get().format(date);
		return string;
	}

    public static Date parseToDateTime(String d) throws ParseException {
        return TIME_FORMATE.get().parse(d);
    }

    public static Date parseToDate(String d) throws ParseException {
        return DATE_FORMATE.get().parse(d);
    }

    /**
     * 根据date转化成string yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatStringOnly(Date date) {
        String formatStr = DATE_FORMATE.get().format(date);
        return formatStr;
    }

    /**
     * 日期加（x）天，时间归零  *x 为负值时为减
     *
     * @param date
     * @param x    天数
     */
    public static Date addDayAndTimeRZ(Date date, int x) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, x);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 将参数中的时分秒 放置在指定日期年月日
     *
     * @param hmsDate
     * @return
     */
    public static Date parseHMSofDate(Date ymdDate, Date hmsDate) {
        ymdDate = ymdDate == null ? new Date() : ymdDate;
        hmsDate = hmsDate == null ? new Date() : hmsDate;
        Calendar cal = Calendar.getInstance();
        cal.setTime(hmsDate);
        Integer hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE), second = cal.get(Calendar.SECOND);
        cal.setTime(ymdDate);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 将XMLGregorianCalendar转化为Date类型
     *
     * @param cal
     * @return
     */
    public static Date xmlDate2Date(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    /**
     * 计算current日期是否在start和end之间，包括相等。
     *
     * @param current 目标日期
     * @param start   开始时间
     * @param end     结束时间
     * @return
     */
    public static boolean between(Date current, Date start, Date end) {
        if (current == null || start == null || end == null)
            return false;
        if (end.before(start)) {
            throw new IllegalArgumentException("start can not big than end");
        }
        int s = current.compareTo(start);//0,1
        int e = current.compareTo(end);//-1,0
        int c = s + e;
        return c != -2 && c != 2;
    }

    private final static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    public static Date mergeDate(Integer date, Integer time) throws ParseException {
        String t = String.valueOf(time);
        while (t.length() < 6) {
            t = "0" + t;
        }
        return df.parse(date + t);
    }
    /**
     * 
     * 计算日期所在月的天数. <br/> 
     * @param date
     * @return
     */
    public static int getMonthDay(Date date){  
	        int count=30;  
	        try {        
	             Calendar calendar = new GregorianCalendar();       
	             calendar.setTime(date);      
	             count=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);      
	        }catch (Exception e) {     
	                 e.printStackTrace();     
	        }  
	        return count;  
   }
  

    /**
     *
     * 计算日期所在周的 星期几. <br/>
     * @param date
     * @return  1代表星期1  2代表星期2
     */
    public static int getWeekDay(Date date){
        int count=1;
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            count=calendar.get(Calendar.DAY_OF_WEEK)-1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }



    /**
     *
     * 计算日期所在周的 星期几. <br/>
     * @param date
     * @return  1代表星期1  2代表星期2
     */
    public static String getWeek(Date date){
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int num = getWeekDay(date);
        return weekDays[num];
    }

    /**
     *
     * 计算日期所在周的 星期几. <br/>
     * @param date
     * @return  1代表星期1  2代表星期2
     */
    public static String getWeek1(Date date){
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        int num = getWeekDay(date);
        return weekDays[num];
    }


    // 获得当前日期相差的天数
    private static int getMondayPlus(int num , Date date) {  
        Calendar cd = Calendar.getInstance();  
        cd.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1  
        if (dayOfWeek == num) {  
            return 0;  
        } else {  
            return num - dayOfWeek;  
        }  
    }   
 	/**
     * 
     * 获得下周几，一般为1~7. <br/> 
     * @param num
     * @return
     */
 	public static Date getNextToday(int num,Date date) { 
        int mondayPlus = getMondayPlus(num,date);  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.setTime(date);
        currentDate.add(currentDate.DATE, mondayPlus + 7);  
        Date monday = currentDate.getTime();  
        return monday;  
    }

    public static void main(String[] args) throws ParseException {
//		Date startDate = new Date(115,0,31);
//		Date tempDate = TimeUtils.DATE_FORMATE.parse("2015-02-10");
//    	tempDate = TimeUtils.parseHMSofDate(tempDate, null);
//    	System.err.println(TimeUtils.TIME_FORMATE.format(tempDate));
//        System.err.println(TimeUtils.TIME_FORMATE.format(TimeUtils.getDateIncDayCount(TimeUtils.DATE_FORMATE.parse("2015-04-11"), -42103)));
        //TimeUtil.setCurrentDate(startDate);

//    	System.out.println(TIME_FORMATE.format(TimeUtils.getDateCurrentMonth(20)) );
//    	Integer period = 33;
//    	Date psDueDate2 = new Date();
//    	Calendar calendar=Calendar.getInstance();
//    	Calendar calendar2=Calendar.getInstance();
//    	calendar.set(2016, 6, 23);
//    	calendar2.set(2018, 4, 20);
//    	Date begin = calendar2.getTime();
//    	Date endDate = calendar.getTime();
//        System.out.println(TimeUtils.getWeekDay( calendar.getTime()));
//        System.out.println(TimeUtils.getDateIncDayCount(calendar.getTime(),23));
//        System.out.print(TimeUtils.getDateIncDayCount(calendar.getTime(),31));
//    	System.out.println("开始日期："+DATE_FORMATE.get().format(begin));
//    	System.out.println("结束日期："+DATE_FORMATE.get().format(endDate));
//		long betweenCycleDay = TimeUtils.calDaysBetweenDate(endDate,begin);//账期日天数
//        System.out.print(betweenCycleDay);
//		BigDecimal betweenPsDueDay = CurrencyUtils.amtSubs(new BigDecimal(period), new BigDecimal(betweenCycleDay));//剩余还款日天数
//		Date psDueDate = TimeUtils.getDateIncDayCount(TimeUtils.getDateIncDayCount(endDate,-1),betweenPsDueDay.intValue());
//		if(betweenPsDueDay.compareTo(new BigDecimal(0))>0){
//			psDueDate2 = TimeUtils.getDateIncDayCount(begin,period);
//		}
//		psDueDate2 = TimeUtils.getDateIncDayCount(begin,period);
//		
//		System.err.println("账期日天数:"+betweenCycleDay);
//		System.err.println("剩余还款日天数:"+betweenPsDueDay);
//		System.err.println("还款日:"+DATE_FORMATE.get().format(psDueDate));
//		
//		System.err.println("开始时间还款日:"+DATE_FORMATE.get().format(psDueDate2));
    	
    	
    	
//    	System.out.println(getNextToday(1,new Date()));
//
//
//        Date date = TimeUtils.getDateIncDayCount(TimeUtils.formatTime2Date(sysDate), -1);
//        System.out.println(date);
    	
//        String date = ymdHMSFormatePRC(new Date());
//        System.out.println(date);
//        Calendar calendar =  Calendar.getInstance();
//        calendar.set(2019, 8, 23);
//        Date d = new Date();
//        calendar.getTime();
//        System.out.println(calendar.getTime());
//        Date endDate = TimeUtils.getDateByMonths(calendar.getTime(),6);
//        System.out.println(endDate);
    	
//        Date now = TimeUtils.formatTimeEndTime(TimeUtils.getDateIncDayCount(TimeUtils.formatTime2Date(d), -1));
//        System.out.println(now);

//        System.out.println(formatString(getDateCurrentMonth(1)));

        



        Calendar cal = Calendar.getInstance();
        	    cal.set(2019, 1, 1);
        	    String beginTime = TimeUtils.formatStringOnly(cal.getTime());
        	    cal.set(Calendar.DAY_OF_MONTH, TimeUtils.getMonthDay(cal.getTime()));
        	    String endTime = TimeUtils.formatString(TimeUtils.formatTimeEndTime(cal.getTime()));

        System.out.println(beginTime+"-->" + endTime);







    }
    /*
    String转date类型，date为string的格式
     */
    public static Date StringToDate(String timeString, String dateFormat){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            date = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }
}