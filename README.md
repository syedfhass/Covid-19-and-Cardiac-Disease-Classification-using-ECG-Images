# Covid-19-and-Cardiac-Disease-Classification-using-ECG-Images

AI powered Android App for Covid-19 and Cardiac Disease Classification using ECG Images

Recent research on the Covid-19 and Cardiac disease classification using ECG images with a feature App development has tried to make a contribution to the society in a very simple yet efficient manner. The data is collected from Mendeley and UCI, and the custom model is built using CNN. The UCI dataset is in a numerical form which is converted into a 12-lead ECG plot. To increase the performance of the model, we have tried to use different techniques such as data augmentation, data scaling, and converting the images to grayscale. The testing of the model was performed with 1 Nvidia T4 GPU, 32GB RAM, and a 1.59GHz memory clock taking 9 hours of run time with a slow learning rate and 300 epochs which gave an accuracy of 86 and 80 on the train and test data, respectively. Taking the help of Android studio and converting the custom model to tflite, we have built our app, which gives an option for people to have a shot at knowing the ECG result during these testing times of covid-19 when the hospital is running out of staff.

## Results

Implementation of the custom CNN model improved the accuracy compared to standard VGG16

On comparing the results of our custom-built model with the Vgg16 model, which was trained on ECG image data, we found that our model's accuracy and performance are higher for our dataset. As shown in the below figures, the custom model has achieved the accuracy of 86 and 80 on train and test, respectively. On the other hand, the VGG-16 model gave an accuracy of 50 and 20 on train and test, respectively.

### Custom model’s Accuracy and Loss Curve

![image](https://user-images.githubusercontent.com/113072173/228134732-6fa098eb-0857-4680-ac69-a478ae022958.png)

### VGG-16 model’s Accuracy and Loss curve

![image](https://user-images.githubusercontent.com/113072173/228135149-d81808ae-6d2b-400e-914b-22c6d6d695e8.png)


## Mobile App Development

The very first point of consideration when thinking about ways to connect an ML model to a mobile app is to create an API that acts as a middle layer between the frontend (App) and the backend (ML model). However, we have adopted an innovative approach of directly integrating the ML model with the android app using the "tflite (TensorFlow lite) model import" feature available in the latest Android Studio 4.1

![image](https://user-images.githubusercontent.com/113072173/228135654-1d83ebad-b508-45d2-a212-0aeb015c2157.png)


*Note:* Dataset & model in h5 format is available at https://drive.google.com/drive/folders/1HVTWX2F3OjMA151FSNkGGbukC1sL4sJq?usp=sharing

