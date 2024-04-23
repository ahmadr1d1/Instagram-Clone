package com.ahmadrd.instagramclone.utils

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import com.ahmadrd.instagramclone.R
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class Util  {
    fun uploadImage(uri: Uri, folderName: String, callback:(String?) -> Unit) {
        var imageUrl: String?
        FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
            .putFile(uri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {image ->
                    imageUrl = image.toString()
                    callback(imageUrl)
                }
            }
    }

    fun uploadVideo(uri: Uri, folderName: String, context: Context,
                    callback: (String?) -> Unit) {
        var videoUrl: String?
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.show()

        FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
            .putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { image ->
                    videoUrl = image.toString()
                    dialog.dismiss()
                    callback(videoUrl)
                }
            }
            .addOnProgressListener { taskSnapshot ->
                // This code not  yet working
                val progress: Double = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                dialog.setMessage("Uploaded $progress %")
            }
    }
}