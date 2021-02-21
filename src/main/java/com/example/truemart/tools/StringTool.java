package com.example.truemart.tools;

public class StringTool {
    public static String standardizedString(String str) {
        char[] charArray = str.toCharArray();
        StringBuilder newStr = new StringBuilder();

        for(int i =0;i<charArray.length;i++) {
            if(i == 0) newStr.append(Character.toUpperCase(charArray[i]));
            else {
                int code = charArray[i];
                if((code >= 97 && code <= 122) || (code >=48 && code <=57)) {
                    newStr.append(charArray[i]);
                } else {
                    newStr.append(" ");
                    newStr.append(charArray[i]);
                }
            }

        }

        return newStr.toString();
    }
}
