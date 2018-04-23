package com.fractal.concordia.mappings;

import java.util.regex.Pattern;

/**
 * Constants used in this project.
 *
 * @author - saumya.goyal
 */
public final class Constants {

	public static final Pattern[] pattern = {
			Pattern.compile("x([1-9]|10)g"), Pattern.compile(" ([1-9]|10)g"), //1-10
			Pattern.compile("x(1[1-9]|20)g"), Pattern.compile(" (1[1-9]|20)g"), //11-20
			Pattern.compile("x(2[1-9]|30)g"), Pattern.compile(" (2[1-9]|30)g"), //21-30
			Pattern.compile("x(3[1-9]|40)g"), Pattern.compile(" (3[1-9]|40)g"), //31-40
			Pattern.compile("x(4[1-9]|50)g"), Pattern.compile(" (4[1-9]|50)g"), //41-50
			Pattern.compile("x(5[1-9]|[6-9][0-9]|100)g"), Pattern.compile(" (5[1-9]|[6-9][0-9]|100)g"), //51-100
			Pattern.compile("x(10[1-9]|1[1-4][0-9]|150)g"), Pattern.compile(" (10[1-9]|1[1-4][0-9]|150)g"), //101-150
			Pattern.compile("x(15[1-9]|1[6-9][0-9]|200)g"), Pattern.compile(" (15[1-9]|1[6-9][0-9]|200)g"), //151-200
			Pattern.compile("x(20[1-9]|2[1-4][0-9]|250)g"), Pattern.compile(" (20[1-9]|2[1-4][0-9]|250)g"), //201-250
			Pattern.compile("x(25[1-9]|2[6-9][0-9]|300)g"), Pattern.compile(" (25[1-9]|2[6-9][0-9]|300)g"), //251-300
			Pattern.compile("x(30[1-9]|3[1-9][0-9]|400)g"), Pattern.compile(" (30[1-9]|3[1-9][0-9]|400)g"), //301-400g
			Pattern.compile("x(40[1-9]|4[1-4][0-9]|450)g"), Pattern.compile(" (40[1-9]|4[1-4][0-9]|450)g"), //401-450g
			Pattern.compile("x(45[1-9]|4[6-9][0-9]|500)g"), Pattern.compile(" (45[1-9]|4[6-9][0-9]|500)g"), //451-500g
			Pattern.compile("x(50[1-9]|5[1-9][0-9]|600)g"), Pattern.compile(" (50[1-9]|5[1-9][0-9]|600)g"), //501-600g
			Pattern.compile("x(60[1-9]|6[1-9][0-9]|700)g"), Pattern.compile(" (60[1-9]|6[1-9][0-9]|700)g"), //601-700g
			Pattern.compile("x(70[1-9]|7[1-4][0-9]|750)g"), Pattern.compile(" (70[1-9]|7[1-4][0-9]|750)g"), //701-750g
			Pattern.compile("x(75[1-9]|7[6-9][0-9]|800)g"), Pattern.compile(" (75[1-9]|7[6-9][0-9]|800)g"), //751-800g
			Pattern.compile("x(80[1-9]|8[1-9][0-9]|900)g"), Pattern.compile(" (80[1-9]|8[1-9][0-9]|900)g"), //801-900g
			Pattern.compile("x(90[1-9]|9[1-9][0-9]|1000)g"), Pattern.compile(" (90[1-9]|9[1-9][0-9]|1000)g"), //901-1000g
			Pattern.compile("x1kg"), Pattern.compile(" 1kg"),
			Pattern.compile("x1.0kg"), Pattern.compile(" 1.0kg"),
			Pattern.compile("x(100[1-9]|10[1-9][0-9]|1100)g"), Pattern.compile(" (100[1-9]|10[1-9][0-9]|1100)g"), //1000-1100g
			Pattern.compile("x1.1kg"), Pattern.compile(" 1.1kg"),
			Pattern.compile("x(110[1-9]|11[1-9][0-9]|1200)g"), Pattern.compile(" (110[1-9]|11[1-9][0-9]|1200)g"), //1100-1200g
			Pattern.compile("x1.2kg"), Pattern.compile(" 1.2kg"),
			Pattern.compile("x(120[1-9]|12[1-9][0-9]|1300)g"), Pattern.compile(" (120[1-9]|12[1-9][0-9]|1300)g"), //1000-1300g
			Pattern.compile("x1.3kg"), Pattern.compile(" 1.3kg"),
			Pattern.compile("x(130[1-9]|13[1-9][0-9]|14[0-9]{2}|1500)g"), Pattern.compile(" (130[1-9]|13[1-9][0-9]|14[0-9]{2}|1500)g"), //1300-1500g
			Pattern.compile("x1.5kg"), Pattern.compile(" 1.5kg"),
			Pattern.compile("x(150[1-9]|15[1-9][0-9]|1[6-9][0-9]{2}|2000)g"), Pattern.compile(" (150[1-9]|15[1-9][0-9]|1[6-9][0-9]{2}|2000)g"), //1501-2000g
			Pattern.compile("x2kg"), Pattern.compile(" 2kg"),
			Pattern.compile("x2.0kg"), Pattern.compile(" 2.0kg")};

	public static final String[] regularex = new String[]{
			"x one:unit ", " one:unit ",
			"x two:unit ", " two:unit ",
			"x three:unit ", " three:unit ",
			"x four:unit ", " four:unit ",
			"x five:unit ", " five:unit ",
			"x ten:unit ", " ten:unit ",
			"x fifteen:unit ", " fifteen:unit ",
			"x twenty:unit ", " twenty:unit ",
			"x twentyfive:unit ", " twentyfive:unit ",
			"x thirty:unit ", " thirty:unit ",
			"x forty:unit ", " forty:unit ",
			"x fortyfive:unit ", " fortyfive:unit ",
			"x fifty:unit ", " fifty:unit ",
			"x sixty:unit ", " sixty:unit ",
			"x seventy:unit ", " seventy:unit ",
			"x seventyfive:unit ", " seventyfive:unit ",
			"x eighty:unit ", " eighty:unit ",
			"x ninety:unit ", " ninety:unit ",
			"x hundred:unit ", " hundred:unit ",
			"x hundred:unit ", " hundred:unit ",
			"x hundred:unit ", " hundred:unit ",
			"x hundredten:unit ", " hundredten:unit ",
			"x hundredten:unit ", " hundredten:unit ",
			"x hundredtwenty:unit ", " hundredtwenty:unit ",
			"x hundredtwenty:unit ", " hundredtwenty:unit ",
			"x hundredthirty:unit ", " hundredthirty:unit ",
			"x hundredthirty:unit ", " hundredthirty:unit ",
			"x hundredfifty:unit ", " hundredfifty:unit ",
			"x hundredfifty:unit ", " hundredfifty:unit ",
			"x twohundred:unit ", " twohundred:unit ",
			"x twohundred:unit ", " twohundred:unit ",
			"x twohundred:unit ", " twohundred:unit "};

	public static final String[] searchmetric = new String[]{
			"0 gr", "0gr", "0 g", "0gm", "0 gm", "0gram", "0 gram", "0grams", "0 grams", "0kg", "0 kg", "0kgs", "0 kgs", "0 ml", "0ml", "0 l", "0l","0 lt", "0lt","0 ltr", "0ltr",
			"1 gr", "1gr", "1 g", "1gm", "1 gm", "1gram", "1 gram", "1grams", "1 grams", "1kg", "1 kg", "1kgs", "1 kgs", "1 ml", "1ml", "1 l", "1l","1 lt", "1lt","1 ltr", "1ltr",
			"2 gr", "2gr", "2 g", "2gm", "2 gm", "2gram", "2 gram", "2grams", "2 grams", "2kg", "2 kg", "2kgs", "2 kgs", "2 ml", "2ml", "2 l", "2l","2 lt", "2lt","2 ltr", "2ltr",
			"3 gr", "3gr", "3 g", "3gm", "3 gm", "3gram", "3 gram", "3grams", "3 grams", "3kg", "3 kg", "3kgs", "3 kgs", "3 ml", "3ml", "3 l", "3l","3 lt", "3lt","3 ltr", "3ltr",
			"4 gr", "4gr", "4 g", "4gm", "4 gm", "4gram", "4 gram", "4grams", "4 grams", "4kg", "4 kg", "4kgs", "4 kgs", "4 ml", "4ml", "4 l", "4l","4 lt", "4lt","4 ltr", "4ltr",
			"5 gr", "5gr", "5 g", "5gm", "5 gm", "5gram", "5 gram", "5grams", "5 grams", "5kg", "5 kg", "5kgs", "5 kgs", "5 ml", "5ml", "5 l", "5l","5 lt", "5lt","5 ltr", "5ltr",
			"6 gr", "6gr", "6 g", "6gm", "6 gm", "6gram", "6 gram", "6grams", "6 grams", "6kg", "6 kg", "6kgs", "6 kgs", "6 ml", "6ml", "6 l", "6l","6 lt", "6lt","6 ltr", "6ltr",
			"7 gr", "7gr", "7 g", "7gm", "7 gm", "7gram", "7 gram", "7grams", "7 grams", "7kg", "7 kg", "7kgs", "7 kgs", "7 ml", "7ml", "7 l", "7l","7 lt", "7lt","7 ltr", "7ltr",
			"8 gr", "8gr", "8 g", "8gm", "8 gm", "8gram", "8 gram", "8grams", "8 grams", "8kg", "8 kg", "8kgs", "8 kgs", "8 ml", "8ml", "8 l", "8l","8 lt", "8lt","8 ltr", "8ltr",
			"9 gr", "9gr", "9 g", "9gm", "9 gm", "9gram", "9 gram", "9grams", "9 grams", "9kg", "9 kg", "9kgs", "9 kgs", "9 ml", "9ml", "9 l", "9l","9 lt", "9lt","9 ltr", "9ltr"};

	public static final String[] replacemetric = new String[]{
			"0g", "0g", "0g", "0g", "0g", "0g", "0g", "0g", "0g", "0kg", "0kg", "0kg", "0kg", "0g", "0g", "0kg", "0kg", "0kg", "0kg", "0kg", "0kg",
			"1g", "1g", "1g", "1g", "1g", "1g", "1g", "1g", "1g", "1kg", "1kg", "1kg", "1kg", "1g", "1g", "1kg", "1kg", "1kg", "1kg", "1kg", "1kg",
			"2g", "2g", "2g", "2g", "2g", "2g", "2g", "2g", "2g", "2kg", "2kg", "2kg", "2kg", "2g", "2g", "2kg", "2kg", "2kg", "2kg", "2kg", "2kg",
			"3g", "3g", "3g", "3g", "3g", "3g", "3g", "3g", "3g", "3kg", "3kg", "3kg", "3kg", "3g", "3g", "3kg", "3kg", "3kg", "3kg", "3kg", "3kg",
			"4g", "4g", "4g", "4g", "4g", "4g", "4g", "4g", "4g", "4kg", "4kg", "4kg", "4kg", "4g", "4g", "4kg", "4kg", "4kg", "4kg", "4kg", "4kg",
			"5g", "5g", "5g", "5g", "5g", "5g", "5g", "5g", "5g", "5kg", "5kg", "5kg", "5kg", "5g", "5g", "5kg", "5kg", "5kg", "5kg", "5kg", "5kg",
			"6g", "6g", "6g", "6g", "6g", "6g", "6g", "6g", "6g", "6kg", "6kg", "6kg", "6kg", "6g", "6g", "6kg", "6kg", "6kg", "6kg", "6kg", "6kg",
			"7g", "7g", "7g", "7g", "7g", "7g", "7g", "7g", "7g", "7kg", "7kg", "7kg", "7kg", "7g", "7g", "7kg", "7kg", "7kg", "7kg", "7kg", "7kg",
			"8g", "8g", "8g", "8g", "8g", "8g", "8g", "8g", "8g", "8kg", "8kg", "8kg", "8kg", "8g", "8g", "8kg", "8kg", "8kg", "8kg", "8kg", "8kg",
			"9g", "9g", "9g", "9g", "9g", "9g", "9g", "9g", "9g", "9kg", "9kg", "9kg", "9kg", "9g", "9g", "9kg", "9kg", "9kg", "9kg", "9kg", "9kg"};

	public static final String[] searchList = new String[]{
			"10g", "10 ",
			"50g", "50 ",
			"100g", "100 ",
			"150g", "150 ",
			"1kg", "1000g", "1000 ",
			"1.2kg", "1200g", "1200 ",
			"1.5kg", "1500g", "1500 ",
			"200g", "200 ",
			"250g", "250 ",
			"2kg", "2000g", "2000 ",
			"2.5kg", "2500g", "2500 ",
			"3kg", "3000g", "3000 ",
			"2.5kg", "3500g", "3500 ",
			"400g", "400 ",
			"450g", "450 ",
			"4kg", "4000g", "4000 ",
			"4.5kg", "4500g", "4500 ",
			".5kg", "500g", "500 ",
			"5kg", "5000g", "5000 ",
			"7.5kg", "750g", "750 ",
			"300g", "300 ",
			"350g", "350 ",
			"600g", "600 ",
			"700g", "700 ",
			"800g", "800 ",
			"900g", "900 "};

	public static final String[] replacementList = new String[]{
			"0.1unit", "0.1unit",
			"0.5unit", "0.5unit",
			"1unit", "1unit",
			"1.5unit", "1.5unit",
			"10unit", "10unit", "10unit",
			"12unit", "12unit", "12unit",
			"15unit", "15unit", "15unit",
			"2unit", "2unit",
			"2.5unit", "2.5unit",
			"20unit", "20unit", "20unit",
			"25unit", "25unit", "25unit",
			"30unit", "30unit", "30unit",
			"35unit", "35unit", "35unit",
			"4unit", "4unit",
			"4.5unit", "4.5unit",
			"40unit", "40unit", "40unit",
			"45unit", "45unit", "45unit",
			"5unit", "5unit", "5unit",
			"50unit", "50unit", "50unit",
			"7.5unit", "7.5unit", "7.5unit",
			"3unit", "3unit",
			"3.5unit", "3.5unit",
			"6unit", "6unit",
			"7unit", "7unit",
			"8unit", "8unit",
			"9unit", "9unit"};

}
