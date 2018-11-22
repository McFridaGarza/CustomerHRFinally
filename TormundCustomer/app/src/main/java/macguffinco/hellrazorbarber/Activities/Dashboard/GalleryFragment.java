package macguffinco.hellrazorbarber.Activities.Dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.common.images.Size;

import macguffinco.hellrazorbarber.Activities.Common.PreviewCamera;
import macguffinco.hellrazorbarber.R;

public class GalleryFragment extends Fragment {


    GridView gridView;
    Button btnCamera;

    //Camera
    private  String cameraId;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    private ImageReader imageReader;


    private  static final int REQUEST_CAMERA_PERMISSSION=200;
    private Size imagesDimension;

    int[] images={R.drawable.ic_menu_send,R.drawable.barbe,R.drawable.logohellrazor,R.drawable.barbero,R.drawable.barber,R.drawable.logohellrazor,R.drawable.barbero,R.drawable.barber};


    CameraDevice.StateCallback stateCallback= new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice=camera;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice=null;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CAMERA_PERMISSSION){

            if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=null;

       try {
            view =inflater.inflate(R.layout.activity_gallery_fragment,container,false);

           gridView=view.findViewById(R.id.gridGallery);
           btnCamera=view.findViewById(R.id.ButtonCamera);
           btnCamera.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent= new Intent(getActivity(), PreviewCamera.class);
                    startActivity(intent);
               }
           });
           GridGalleryAdapter gridAdapter=new GridGalleryAdapter(getContext(),images);

           gridView.setAdapter(gridAdapter);


       }catch (Exception ec){

           String hola= ec.getMessage();
       }


        return view;



    }

    private void OpenCamera(){
        CameraManager manager=(CameraManager)getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId=manager.getCameraIdList()[0];
            CameraCharacteristics characteristics= manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map!=null;
            imagesDimension=Size.parseSize("400*400");
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CAMERA_PERMISSSION);

                return;
            }
            manager.openCamera(cameraId,stateCallback,null);
        }catch (Exception ex){
            String hola= ex.getMessage();
        }

    }

    public void onClickTakePicture(View view){

        try {

            //Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //getActivity().startActivityForResult(intent,0);

        }catch (Exception e){
            String hola= e.getMessage();

        }


    }
}
