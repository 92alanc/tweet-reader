# Model classes
-keep class com.alancamargo.tweetreader.model.** { *; }

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

# Jsoup
-keeppackagenames org.jsoup.nodes