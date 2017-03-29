# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# The following directives are optional
-dontwarn android.support.**
-dontnote android.support.**
-dontnote android.net.**
-dontnote com.android.vending.**
-dontnote com.google.vending.**
-dontnote org.apache.http.**

# The following -keep directives are required
# when consuming the Sinch Verification library

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class com.sinch.** extends android.os.AsyncTask {
    *;
}

-keep,includedescriptorclasses class com.sinch.** {
    *;
}

