package macguffinco.hellrazorbarber.Activities.Common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import macguffinco.hellrazorbarber.R;

public class PreviewCamera extends AppCompatActivity {


    private Button takenPicture;
    private TextureView textureViewCamera;

    //Camera
    public  String cameraId;
    public CameraDevice cameraDevice;
    public CameraCaptureSession cameraCaptureSession;
    public CaptureRequest.Builder captureRequestBuilder;
    public ImageReader imageReader;

    public Size imagesDimension;

    //Check state orientation of output image
    public static final SparseIntArray ORIENTATIONS=  new SparseIntArray();
    static {

        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);

    }

    //Save to FIle

    public File file;
    public  static final int REQUEST_CAMERA_PERMISSSION=200;
    public boolean mFlashSupported;
    public Handler mBackgroundHandler;
    public HandlerThread mBackgroundThread;


    private final CameraDevice.StateCallback stateCallback= new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice=camera;
            CreateCameraPreview();
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


        if(requestCode==REQUEST_CAMERA_PERMISSSION){

            if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_preview_camera);

        textureViewCamera=(TextureView)findViewById(R.id.textureViewCamera);
        assert textureViewCamera!=null;
        textureViewCamera.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                OpenCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        takenPicture=(Button) findViewById(R.id.takenPicture);
        assert takenPicture!=null;

        takenPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePicture();
            }


        });
    }


    private void TakePicture() {

        if(cameraDevice==null){
            return;
        }
        try {
            CameraManager manager= (CameraManager)getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics characteristics=manager.getCameraCharacteristics(cameraDevice.getId());

            Size[] jpegSizes=null;
            if(characteristics!=null){

                jpegSizes=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG);

                //Capture Image with customs Size
                int width=640;
                int height=480;

                if(jpegSizes!=null && jpegSizes.length>0){
                    width=jpegSizes[0].getWidth();
                    height=jpegSizes[0].getHeight();
                }

                 imageReader=ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
                List<Surface> outputSurface=new ArrayList<>(2);
                outputSurface.add(imageReader.getSurface());
                outputSurface.add(new Surface(textureViewCamera.getSurfaceTexture()));

                captureRequestBuilder= cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureRequestBuilder.addTarget(imageReader.getSurface());
                captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);

                //Check Orientation base device
                int rotation=getWindowManager().getDefaultDisplay().getRotation();
                captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

                 file = new File(Environment.getExternalStorageDirectory()+"/"+UUID.randomUUID().toString());
                ImageReader.OnImageAvailableListener readerListener=new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader reader) {
                        Image image=null;
                        try {
                            image= imageReader.acquireLatestImage();
                            ByteBuffer buffer= image.getPlanes()[0].getBuffer();
                            byte[] bytes= new byte[buffer.capacity()];
                            buffer.get(bytes);
                            Save(bytes);
                        }catch (FileNotFoundException ex   ){
                            ex.printStackTrace();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }finally {
                            if(image!=null){
                                image.close();
                            }
                        }
                    }

                    private void Save(byte[] bytes) throws IOException{
                        OutputStream outputStream=null;
                        try {
                            outputStream=new FileOutputStream(file);
                            outputStream.write(bytes);
                        }finally {
                            if(outputStream!=null){
                                outputStream.close();
                            }
                        }
                    }

                };

                imageReader.setOnImageAvailableListener(readerListener,mBackgroundHandler);
                final CameraCaptureSession.CaptureCallback captureListener=new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);
                        Toast.makeText(PreviewCamera.this,"Guardada "+file,Toast.LENGTH_SHORT).show();
                        CreateCameraPreview();
                    }
                };

                cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {
                        try {
                            cameraCaptureSession.capture(captureRequestBuilder.build(),captureListener,mBackgroundHandler);

                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                        try {
                           session.abortCaptures();

                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                },mBackgroundHandler);

            }
        }catch (CameraAccessException ex )
        {
            ex.printStackTrace();
        }
    }

    private void CreateCameraPreview(){

        try {
            SurfaceTexture  texture =textureViewCamera.getSurfaceTexture();
            assert  texture!=null;
            texture.setDefaultBufferSize(imagesDimension.getWidth(),imagesDimension.getHeight());
            Surface surface= new Surface(texture);
            captureRequestBuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if(cameraDevice==null) return;
                    cameraCaptureSession=session;
                    UpdatePreview();
                    }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            },null);
        }catch (CameraAccessException ex){
        ex.printStackTrace();
        }
    }

    private void UpdatePreview() {
        if(cameraDevice==null) return;
        //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();

        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(),null,mBackgroundHandler);
        }catch (CameraAccessException ex ){

            ex.printStackTrace();
        }
    }

    private void OpenCamera(){
        CameraManager manager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId=manager.getCameraIdList()[0];
            CameraCharacteristics characteristics= manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map!=null;
            imagesDimension= map.getOutputSizes(SurfaceTexture.class)[0];
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CAMERA_PERMISSSION);

                return;
            }
            manager.openCamera(cameraId,stateCallback,null);
        }catch (Exception ex){
            String hola= ex.getMessage();
        }

    }

TextureView.SurfaceTextureListener textureListener=  new TextureView.SurfaceTextureListener() {
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        OpenCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
};
    
    @Override
    protected void onResume() {
        super.onResume();
        startBackGroundThread();
        if(textureViewCamera.isAvailable()){
            OpenCamera();
        }else{
            textureViewCamera.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBackGroundThread();
    }

    private void startBackGroundThread() {
        mBackgroundThread=new HandlerThread("Camera BackGround");
        mBackgroundThread.start();
        mBackgroundHandler=new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackGroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread=null;
            mBackgroundHandler=null;
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

    }

}
