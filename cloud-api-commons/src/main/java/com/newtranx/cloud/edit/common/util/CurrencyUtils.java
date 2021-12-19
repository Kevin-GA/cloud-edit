package com.newtranx.cloud.edit.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * 描述: 系统通用金额计算工具类
 */
public class CurrencyUtils{

	private final static int DEFAULT_AMT_DIGTIAL_COUNT = 2;
	
	private final static int DEFAULT_AMT_COMPARE_DIGTIAL_COUNT = 11; 
	/**
	 * 描述: 获取最小值
	 * @param amt0
	 * @param amt1
	 * @return
	 */
	public static BigDecimal min(BigDecimal amt0, BigDecimal amt1) {
		if (isAmtGreat(amt0, amt1)) {
			return amt1;
		}// 小于等于
		return amt0;
	}

    /**
     * 描述: 获取最小值
     *
     * @param amt0
     * @param amt1
     * @return
     */
    public static BigDecimal max(BigDecimal amt0, BigDecimal amt1) {
        if (isAmtGreat(amt0, amt1)) {
            return amt0;
        }
        return amt1;
    }

	/**
	 * 描述: 字符串转化为BigDecimal类型
	 * @param amt0 需要转换的数值
	 * @return
	 */
	public static BigDecimal getBigDecimal(String amt0) {
		if(amt0==null || "".equals(amt0.trim())){
			return BigDecimal.ZERO;
		}
		amt0 = amt0.trim();
		BigDecimal amt = new BigDecimal(amt0);
		return getZeroBigDecimalIfNull(amt);
	}

	/**
	 * 描述: 数据直接修约
	 *
	 * @param amt
	 *            需修约的数字
	 * @param reservedDigCount
	 *            保留精度
	 * @param roundingMode
	 *            修约方式
	 * @return
	 */
	public static BigDecimal roundingResult(BigDecimal amt, int reservedDigCount, RoundingMode roundingMode) {
		amt = getZeroBigDecimalIfNull(amt);
		amt = amt.setScale(reservedDigCount, roundingMode);
		return amt;
	}

	/**
	 * 描述: 数据直接修约
	 *
	 * @param amt
	 *            需修约的数字
	 * @return
	 */
	public static BigDecimal getBigDecimalWhenEvenDown(BigDecimal amt) {
		amt = roundingResult(amt, CurrencyUtils.DEFAULT_AMT_DIGTIAL_COUNT,RoundingMode.DOWN);
		return amt;
	}
	public static BigDecimal getBigDecimalWhenEvenUp(BigDecimal amt) {
		amt = roundingResult(amt, CurrencyUtils.DEFAULT_AMT_DIGTIAL_COUNT,RoundingMode.HALF_UP);
		return amt;
	}

	/**
	 * 描述: 第一个数是比较第二个数 如果左边>右边则返回>0 如果相等 返回0 如果左边<右边返回负数
	 *
	 * @param amt0
	 * @param amt1
	 * @return
	 */
	public static int amtCompare(BigDecimal amt0, BigDecimal amt1) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		return amt0.compareTo(amt1);
	}

	/**
	 * 描述: 判断第一个数是否等于第二个数
	 *
	 * @param amt0
	 *            第一个数
	 * @param amt1
	 *            第二个数
	 * @return
	 */
	public static boolean isAmtEqual(BigDecimal amt0, BigDecimal amt1) {
		if (amtCompare(amt0, amt1) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 描述: 判断第一个数是否大于第二个数
	 *
	 * @param amt0
	 *            第一个数
	 * @param amt1
	 *            第二个数
	 * @return
	 */
	public static boolean isAmtGreat(BigDecimal amt0, BigDecimal amt1) {
		if (amtCompare(amt0, amt1) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 描述: 判断第一个数是否大于等于第二个数
	 *
	 * @param amt0
	 *            第一个数
	 * @param amt1
	 *            第二个数
	 * @return
	 */
	public static boolean isAmtGreatAndEqual(BigDecimal amt0, BigDecimal amt1) {
		if (amtCompare(amt0, amt1) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 描述: 判断金额是否大于0
	 *
	 * @param amt0
	 *            需要判断的金额
	 * @return
	 */
	public static boolean isAmtGreatThanZero(BigDecimal amt0) {
		if (amtCompare(amt0, BigDecimal.ZERO) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 描述: 判断金额是否大于等于0
	 *
	 * @param amt0
	 *            需要判断的金额
	 * @return
	 */
	public static boolean isAmtGreatThanOrEqualZero(BigDecimal amt0) {
		if (amtCompare(amt0, BigDecimal.ZERO) >= 0) {
			return true;
		}
		return false;
	}


	/**
	 * 描述: 判断金额是否小于0
	 *
	 * @param amt0
	 *            需要判断的金额
	 * @return
	 */
	public static boolean isAmtLessThanZero(BigDecimal amt0) {
		if (amtCompare(amt0, BigDecimal.ZERO) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * 描述:判断金额是否小于等于0
	 *
	 * @param amt0
	 *            需要判断的金额
	 * @return
	 */
	public static boolean isAmtLessThanOrEqualZero(BigDecimal amt0) {
		if (amtCompare(amt0, BigDecimal.ZERO) <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 描述: 判断金额是否等于0
	 *
	 * @param amt0
	 *            需要判断的金额
	 * @return
	 */
	public static boolean isAmtEqualZero(BigDecimal amt0) {
		if (amtCompare(amt0, BigDecimal.ZERO) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 描述: 格式化浮点数
	 *
	 * @param digital
	 *            需要格式化的数字
	 * @param count
	 *            保留精度
	 * @return
	 */
	public static String formatDigital(double digital, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append("0");
		}
		NumberFormat format = new DecimalFormat("#0." + sb.toString());
		return format.format(digital);
	}

	/**
	 * 描述: 如果input为空返回零
	 *
	 * @param input
	 *            判断值
	 * @return
	 */
	public static BigDecimal getZeroBigDecimalIfNull(BigDecimal input) {
		return input == null ? BigDecimal.ZERO : input;
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相加
	 * @param amt0 第一个金额
	 * @param amt1 第二个金额
	 * @return
	 */
	public static BigDecimal amtAdd(BigDecimal amt0, BigDecimal amt1) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		return getBigDecimalWhenEvenUp(amt0.add(amt1));
	}
	
	/**
	 * 描述:计算多个金额相加
	 * @param amts 多个金额参数
	 * @return
	 */
	public static BigDecimal amtAddSeries(BigDecimal amt0,BigDecimal ... amts) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		for(BigDecimal amt : amts){
			amt0 = amt0.add(getZeroBigDecimalIfNull(amt));
		}
		return getBigDecimalWhenEvenUp(amt0);
	}
	
	/**
	 * 
	 * 描述: 计算三个金额相加
	 * @param amt0 第一个金额
	 * @param amt1 第二个金额
	 * @param amt2 第三个金额
	 * @return
	 */
	public static BigDecimal amtAddThree(BigDecimal amt0, BigDecimal amt1,BigDecimal amt2) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		amt2 = getZeroBigDecimalIfNull(amt2);
		return getBigDecimalWhenEvenUp(amt0.add(amt1).add(amt2));
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相减
	 * @param amt0 第一个金额（被减数）
	 * @param amt1 第二个金额（减数）
	 * @return
	 */
	public static BigDecimal amtSubs(BigDecimal amt0, BigDecimal amt1) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		return getBigDecimalWhenEvenUp(amt0.subtract(amt1));
	}

	/**
	 *
	 * 描述: 计算两个金额相减
	 * @param amt0 第一个金额（被减数）
	 * @param amt1 第二个金额（减数）
	 * @param reservedDigCount 保留精度
	 * @return
	 */
	public static BigDecimal amtSubs(BigDecimal amt0, BigDecimal amt1 ,int reservedDigCount) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		BigDecimal amt = roundingResult(amt0.subtract(amt1), reservedDigCount,RoundingMode.HALF_UP);
		return amt;
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相减
	 * @param amt0 第一个金额（被减数）
	 * @param amt1 第二个金额（减数）
	 * @return
	 */
	public static BigDecimal amtSubsMinus2Zero(BigDecimal amt0, BigDecimal amt1) {
		BigDecimal amtTemp = amtSubs(amt0, amt1);
		return isAmtLessThanZero(amtTemp)?BigDecimal.ZERO:amtTemp;
	}
	
	/**
	 * 描述:计算多个金额相减
	 * @param amts 多个金额参数
	 * @return
	 */
	public static BigDecimal amtSubsSeries(BigDecimal amt0,BigDecimal ... amts) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		for(BigDecimal amt : amts){
			amt0 = amt0.subtract(getZeroBigDecimalIfNull(amt));
		}
		return getBigDecimalWhenEvenUp(amt0);
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相乘
	 * @param amt0 第一个金额
	 * @param amt1 第二个金额
	 * @return
	 */
	public static BigDecimal amtMult(BigDecimal amt0, BigDecimal amt1) {
		return amtMult(amt0, amt1,2, RoundingMode.HALF_UP);
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相乘
	 * @param amt0 第一个金额
	 * @param amt1 第二个金额
	 * @param reservedDigCount 数据保留精度
	 * @param roundingMode 数据舍入方式
	 * @return
	 */
	public static BigDecimal amtMult(BigDecimal amt0, BigDecimal amt1,int reservedDigCount, RoundingMode roundingMode) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		return  roundingResult(amt0.multiply(amt1,MathContext.DECIMAL128),reservedDigCount,roundingMode);
	}
	
	/**
	 * 描述:计算多个金额相乘
	 * @param amts 多个金额参数
	 * @return
	 */
	public static BigDecimal amtMultSeries(BigDecimal amt0,int reservedDigCount, RoundingMode roundingMode,BigDecimal ... amts) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		for(BigDecimal amt : amts){
			amt0 = amtMult(amt0,getZeroBigDecimalIfNull(amt),reservedDigCount,roundingMode);
		}
		return getBigDecimalWhenEvenUp(amt0);
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相除
	 * @param amt0 第一个金额（被除数）
	 * @param amt1 第二个金额（除数）
	 * @return
	 */
	public static BigDecimal amtDiv(BigDecimal amt0, BigDecimal amt1) {
		return amtDiv(amt0,amt1,CurrencyUtils.DEFAULT_AMT_COMPARE_DIGTIAL_COUNT,RoundingMode.HALF_UP);
	}
	
	/**
	 * 
	 * 描述: 计算两个金额相除
	 * @param amt0 第一个金额（被除数）
	 * @param amt1 第二个金额（除数）
	 * @param reservedDigCount 数据保留精度
	 * @param roundingMode 数据舍入方式
	 * @return
	 */
	public static BigDecimal amtDiv(BigDecimal amt0, BigDecimal amt1,int reservedDigCount, RoundingMode roundingMode) {
		amt0 = getZeroBigDecimalIfNull(amt0);
		amt1 = getZeroBigDecimalIfNull(amt1);
		if(isAmtEqualZero(amt1)){
			throw new RuntimeException("除数为空！");
		}
		return amt0.divide(amt1,reservedDigCount,roundingMode);
	}
	
	/**
	 * 描述: 数据直接修约
	 * @param amt 需修约的数字
	 * @param roundingMode 修约方法
	 * @return
	 */
	public static BigDecimal getBigDecimalWhenEvenUp(BigDecimal amt,RoundingMode roundingMode) {
		amt = roundingResult(amt,CurrencyUtils.DEFAULT_AMT_DIGTIAL_COUNT,roundingMode);
		return amt;
	}
	
	/**
	 * 描述: 取数据对立值（正数变负数，负数变正数）
	 * @param amt
	 * @return
	 */
	public static BigDecimal amtNegate(BigDecimal amt){
		amt = amt.negate(MathContext.DECIMAL128);
		return amt.setScale(DEFAULT_AMT_DIGTIAL_COUNT, RoundingMode.HALF_UP);
	}
	
	/**
	 * 
	 * 描述:去除空格
	 * @param str 需要去除空格的字符串
	 * @return
	 */
	public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }
	
	/**
	 * 
	 * 描述:转换为千分符格式
	 * @param amt
	 * @return
	 */
	public static String trimToThousands(BigDecimal amt) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		return df.format(amt);  
	}
	
	/**
	 * 根据key算出对应的hash值
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static int murmurHash2(String key) throws UnsupportedEncodingException {
        if (key == null)
            throw new IllegalArgumentException("key can not null");
        String charset = "UTF-8";
        int seed = 0xc6a4a793;
        int m = 0x5bd1e995;
        int r = 24;
        byte[] data = key.getBytes(charset);
        int len = data.length;
        int h = seed ^ len;
        int i = 0;
        while (len >= 4) {
            int k = data[i] & 0xFF;
            k |= (data[i + 1] & 0xFF) << 8;
            k |= (data[i + 2] & 0xFF) << 16;
            k |= (data[i + 3] & 0xFF) << 24;

            k *= m;
            k ^= k >>> r;
            k *= m;

            h *= m;
            h ^= k;

            i += 4;
            len -= 4;
        }

        switch (len) {
            case 3:
                h ^= (data[i + 2] & 0xFF) << 16;
            case 2:
                h ^= (data[i + 1] & 0xFF) << 8;
            case 1:
                h ^= (data[i] & 0xFF);
             
   h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        return h;
    }
	
	
	public static void main(String[] args) {
//		BigDecimal one = new BigDecimal("1.23");
//		BigDecimal two = new BigDecimal("2.45");
//		BigDecimal three = new BigDecimal("3.56");
//		BigDecimal sum = CurrencyUtils.amtAddThree(one, two, three);
//		System.out.println(sum);

		BigDecimal a = new BigDecimal("0.007");
		BigDecimal b = new BigDecimal("0.005");
		BigDecimal c = CurrencyUtils.amtSubs(a,b,9);
		System.out.println(c);

	}
}
