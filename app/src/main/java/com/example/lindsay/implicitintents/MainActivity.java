package com.example.lindsay.implicitintents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void process(View view){

        Intent intent =null, chooser=null;

        if(view.getId()==R.id.LaunchMap){
            //standard action of viewing something
            intent = new Intent(Intent.ACTION_VIEW);
            //set data with URI data: latitude, longitude
            //May not be able to run on an emulator, if there isn't an app to handle it
            intent.setData(Uri.parse("geo:19.076,72.8777"));
            //The chooser intent will have a dialog asking the user to pick an app to run the map
            //Or will say there aren't any apps that can run the intent
            chooser = Intent.createChooser(intent, "@string/launchMap");

            //startActivity(intent);
            //Better to use a chooser intent, and start it instead of the other intent object
            //This can prevent the app from crashing if there isn't an app that can handle the
            //intent you're trying to use
            startActivity(chooser);
        }
        if(view.getId()==R.id.LaunchMarket){
            intent = new Intent(Intent.ACTION_VIEW);
            //can't use a plain URL, specify market for google play market and use ://
            intent.setData(Uri.parse("market://details?id=dolphin.developers.com"));
            chooser=Intent.createChooser(intent, "@string/launchMarket");
            startActivity(chooser);
        }
        if(view.getId()==R.id.SendEmail){
            //Using the action send. There are a lot of built-in actions available
            intent = new Intent(Intent.ACTION_SEND);
            //Uri data format is mailto:
            intent.setData(Uri.parse("mailto:"));

            //don't need to define from, since e-mail sent on an android device you need to log into
            //a gmail account. (?)

            //define who the e-mail is being sent to, using an array of strings
            String[] to = {"email_1@domain.com","email_2@domain.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);

            //define the subject
            intent.putExtra(Intent.EXTRA_EMAIL, "Sent from ImplicitIntents App");

            //define the message being sent
            intent.putExtra(Intent.EXTRA_EMAIL, "First e-mail message sent from my app");

            //need to set the MIME type. rfc822 is for e-mail
            intent.setType("message/rfc822");

            chooser=Intent.createChooser(intent, "@string/sendEmail");
            startActivity(chooser);
        }
        //Send an image from within the app
        if(view.getId()==R.id.SendImage){
            //get an image from the app. need to specify the package name, and the folder (drawable)
            //don't manually enter the image name, but use R
            Uri imageUri=Uri.parse("android.resource://com.example.lindsay.implicitintents"+R.drawable.ic_launcher);

            //sending something, so use an action for sending
            intent = new Intent(Intent.ACTION_SEND);

            //need to specify the MIME type as an image. The * is for any image format
            intent.setType("image/*");

            //give the intent the imageUri created earlier
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.putExtra(Intent.EXTRA_TEXT, "Image attached");
            chooser=Intent.createChooser(intent, "@string/sendImage");
            startActivity(chooser);
        }
        //send images from an external SD card
        if(view.getId()==R.id.SendImages){
            //reference the folder where images are stored on an SD card
            File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //get the list of pictures, which returns as a String array
            String[] listOfPictures = pictures.list();
            Uri uri = null;
            ArrayList<Uri> arrayList = new ArrayList<Uri>();
            for(String picture : listOfPictures){
                //give full Uri of the picture, display as a toast
                //Used a seperate class with static methods, so don't have to retype log messages or toasts
                L.displayToast(this, "file://" + pictures.toString() + "/" + picture);
                //store the Uri in a temp Uri variable
                uri = Uri.parse("file://" + pictures.toString() + "/" + picture);
                //add to an array list of type Uri
                arrayList.add(uri);
            }
            //sending multiple items
            intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            //specify the MIME type of the data
            intent.setType("image/*");
            //adding the arrayList to the intent
            intent.putExtra(Intent.EXTRA_STREAM, arrayList);
            chooser=Intent.createChooser(intent, "@string/sendImages");
            startActivity(chooser);
        }
    }
}
