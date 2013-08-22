android-imgpro-lib
==================

Java Image Processing Library for Android

This Android library project is collection of image processing actions. The term "action" here refers to processing actions found in image editor softwares (e.g. Adobe Photoshop) such as brightness, blending with other bitmap, saturation, blurring, etc. 

Available actions:

* Brightness 
* Desaturate (a.k.a. greyscale)
* Blend (with all Photoshop's blending mode such as overlay, hard light, etc)
* Simple Blur
* Contrast (still WIP)
* Saturation (still WIP)

There's also a helper class `Filter` that contains chain of actions. Just imagine you can combine several actions together to create photo filters like Instagram. 


Some code examples:

```java

Bitmap A = ...;
Bitmap B = ...;

// Put B on top of A with soft light mode and 50% opacity
new Blend(B, 0.5, Blend.Mode.SOFT_LIGHT).adjustBitmap(A); 

// Blend A against ITSELF with overlay mode and 100% opacity
new Blend(1, Blend.Mode.OVERLAY).adjustBitmap(A);

// Blur A with 5 pixels radius
new Blur(5).adjustBitmap(A);

// Darken A by 30 units (the argument should be between -255 and 255)
new Brightness(-30).adjustBitmap(A);


```

Still on heavy development. Expect more documentation soon :)

