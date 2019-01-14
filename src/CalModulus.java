import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalModulus {
    public static double cal(double width1,double height1,double width2,double height2){
        double modulus=0;
        //计算模位数1 modulus1=（成品尺寸宽/模位尺寸宽）*（成品尺寸高/模位尺寸高）
        double modulus1=Math.ceil(width1/width2)*Math.ceil(height1/height2);

        //计算模位数1 modulus1=（成品尺寸宽/模位尺寸高）*（成品尺寸高/模位尺寸宽）
        double modulus2=Math.ceil(width1/height2)*Math.ceil(height1/width2);
        System.out.println("modulus1:"+modulus1+"  "+"modulus2:"+modulus2);
      if (modulus1>modulus2){
          modulus=modulus2;
      }else {
          modulus=modulus1;
      }
        return modulus;

    }
    public static void main(String[] args){
       /* double w1=628;
        double h1=283;
        double mp1=cal(w1+2,h1+2,90,54);
        double hb1=cal(w1,h1,210,285);
        double hb2=cal(w1+4,h1+4,214,289);
        System.out.println("名片模位计算:"+mp1);
        System.out.println("单页，纸张150g模位计算:"+hb1);
        System.out.println("单页，纸张非150g模位计算:"+hb2);
        */
        double s=modulusCompute(27,"250克铜版纸", new BigDecimal( 852), new BigDecimal(574),"Y");
        System.out.println(s);
    }
    public static double modulusCompute(Integer productId, String paperName, BigDecimal width, BigDecimal
            height, String needBleed) {
        BigDecimal dieWidth;    //模位尺寸的宽
        BigDecimal dieHeight;   //模位尺寸的高
        //合版名片
        if (productId.equals(23)) {
            dieWidth = new BigDecimal(92);
            dieHeight = new BigDecimal(56);
            if (needBleed.equals("Y")) {
                //未加出血，需要加出血，名片两边各加1mm
                width = width.add(new BigDecimal(2));
                height = height.add(new BigDecimal(2));
            }
            Double a = Math.ceil(Double.parseDouble(width.divide(dieWidth, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieHeight, RoundingMode.UP).toString()));
            Double b = Math.ceil(Double.parseDouble(width.divide(dieHeight, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieWidth, RoundingMode.UP).toString()));
            return (a.compareTo(b) > 0 ? b : a).intValue();
        }
        //合版单页
        if (productId.equals(27) || productId.equals(62)) {
            if (needBleed.equals("Y")) {
                //未加出血，需要加出血，单页150克=0mm，单页非150克=2mmX2
                if (paperName.indexOf("150克") >= 0) {
                    dieWidth = new BigDecimal(210);
                    dieHeight = new BigDecimal(285);
                    //当“成品尺寸宽+出血<=模位尺寸宽 且 成品尺寸高+出血<=模位尺寸高/2”或者“成品尺寸宽+出血<=模位尺寸高 且 成品尺寸高+出血<=模位尺寸宽/2”,模位数为 0.5
                    if ((width.compareTo(dieWidth) <= 0 && height.compareTo(dieHeight.divide(new BigDecimal(2))) <= 0) || (height.compareTo(dieWidth) <= 0 && width.compareTo(dieHeight.divide(new BigDecimal(2))) <= 0)) {
                        return 0.5;
                    } else {
                        Double a = Math.ceil(Double.parseDouble(width.divide(dieWidth, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieHeight, RoundingMode.UP).toString()));
                        Double b = Math.ceil(Double.parseDouble(width.divide(dieHeight, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieWidth, RoundingMode.UP).toString()));
                        return (a.compareTo(b) > 0 ? b : a).intValue();
                    }
                } else {
                    width = width.add(new BigDecimal(4));
                    height = height.add(new BigDecimal(4));
                    dieWidth = new BigDecimal(214);
                    dieHeight = new BigDecimal(289);
                    //当“成品尺寸宽+出血<=模位尺寸宽 且 成品尺寸高+出血<=模位尺寸高/2”或者“成品尺寸宽+出血<=模位尺寸高 且 成品尺寸高+出血<=模位尺寸宽/2”,模位数为 0.5
                    if ((width.compareTo(dieWidth) <= 0 && height.compareTo(dieHeight.divide(new BigDecimal(2))) <= 0) || (height.compareTo(dieWidth) <= 0 && width.compareTo(dieHeight.divide(new BigDecimal(2))) <= 0)) {
                        return 0.5;
                    } else if (width.compareTo(dieHeight.multiply(BigDecimal.valueOf(2))) > 0 || height.compareTo(dieHeight.multiply(BigDecimal.valueOf(2))) > 0) {
                        if (width.compareTo(height) > 0) {
                            return Math.ceil(Double.parseDouble(width.divide(dieWidth, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieHeight, RoundingMode.UP).toString()));
                        } else {
                            return Math.ceil(Double.parseDouble(width.divide(dieHeight, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieWidth, RoundingMode.UP).toString()));
                        }
                    } else {
                        Double a = Math.ceil(Double.parseDouble(width.divide(dieWidth, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieHeight, RoundingMode.UP).toString()));
                        Double b = Math.ceil(Double.parseDouble(width.divide(dieHeight, RoundingMode.UP).toString())) * Math.ceil(Double.parseDouble(height.divide(dieWidth, RoundingMode.UP).toString()));
                        return (a.compareTo(b) > 0 ? b : a).intValue();
                    }
                }
            }
        }
        return 0;
    }


}
