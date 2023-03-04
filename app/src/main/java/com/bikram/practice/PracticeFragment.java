package com.bikram.practice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String modelNameD;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PracticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PracticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PracticeFragment newInstance(String param1, String param2) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //https://stackoverflow.com/questions/26819429/cannot-start-this-animator-on-a-detached-view-reveal-effect
        View view = inflater.inflate(R.layout.fragment_practice, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Practice");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("HandSignDetector", DownloadType.LATEST_MODEL, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel model) {
                        // Download complete. Depending on your app, you could enable
                        // the ML feature, or switch from the local model to the remote
                        // model, etc.
//                        Toast.makeText(activity, "Downloaded ", Toast.LENGTH_SHORT).show();
                        copyFile(Objects.requireNonNull(model.getLocalFilePath()));
                    }
                });
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                int cx = (view.getLeft() + view.getRight()) / 2;
                int cy = (view.getTop() + view.getBottom()) / 2;
                int finalRadius = Math.max(view.getWidth(), view.getHeight());

                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
                int color = Color.parseColor("#FFECB3");
                view.setBackgroundColor(color);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                anim.start();
            }
        }, 0);
        // Toast.makeText(view.getContext(), "Practicefrag", Toast.LENGTH_SHORT).show();
        Button button = view.findViewById(R.id.start_recog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dmodel - downloaded model
                Intent intent = new Intent(getContext(), CameraActivity.class);
                intent.putExtra("dmodel", modelNameD);
                startActivity(intent);
            }
        });
        return view;
    }

    private void copyFile(@NonNull String localFilePath) {
        String[] parts = localFilePath.split("/");
        String uid = parts[parts.length - 3];
        System.out.println(uid);
//        Log.d("check", getContext().getFilesDir().toString());
        File filePath = getContext().getFilesDir();
        String filePathString = filePath.toString();
        File fileDir = new File(filePathString);

        String[] files = fileDir.list();
        assert files != null;
        for (String fname :
                files) {
            Log.i("files",fname);
        }
        if(files.length == 0){
            Log.w("check1", "NO FILES IN FOLDER");
        }

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            Toast.makeText(getContext(), String.valueOf(sd.canRead()), Toast.LENGTH_SHORT).show();
            if(sd.canRead()){
                String currentModelPath = "//data//"+ requireContext().getPackageName()+"//no_backup//com.google.firebase.ml.custom.models//"+uid+"//HandSignDetector//"+parts[parts.length - 1];
                String backupModelPath = "//data//"+ requireContext().getPackageName()+"//files//0.tflite";
                File currentModel = new File(data,currentModelPath);
                File backupModel = new File(data,backupModelPath);
                if (currentModel.exists()){

                    FileChannel src = new FileInputStream(currentModel).getChannel();
                    Toast.makeText(getContext(), String.valueOf(src), Toast.LENGTH_SHORT).show();
                    FileChannel dst = new FileOutputStream(backupModel).getChannel();
                    dst.transferFrom(src,0,src.size());
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    src.close();
                    dst.close();
                }
                else
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("error",e.getMessage());
        }
    }
}