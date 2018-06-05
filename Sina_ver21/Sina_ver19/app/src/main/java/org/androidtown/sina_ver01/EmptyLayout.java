package org.androidtown.sina_ver01;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class EmptyLayout extends AppCompatActivity implements TextEditorDialogFragment.OnTextLayerCallback{
    DiaryData data = new DiaryData();
    private ImageButton save_Btn;
    protected MotionView motionView;
    protected View textEntityEditPanel;
    protected ImageButton textwrite;
    protected ImageButton paint;
    protected ImageButton erase;
    protected ImageButton picture;
    Adapter adapter;
    ViewPager viewPager;
    EmptyView emptyView;
    private Uri mImageCaptureUri;
    ImageView image;
    private String absolutePath;
    Bitmap photo;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;


    final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback() {
        @Override
        public void onEntitySelected(@Nullable MotionEntity entity) {
            if (entity instanceof TextEntity) {
                textEntityEditPanel.setVisibility(View.VISIBLE);
            } else {
                textEntityEditPanel.setVisibility(View.GONE);
            }
        }

        @Override
        public void onEntityDoubleTap(@NonNull MotionEntity entity) {
            startTextEntityEditing();
        }
    };

    private FontProvider fontProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_layout);
       // checkPermission();

        picture = (ImageButton) findViewById(R.id.upload_btn);

        viewPager = (ViewPager) findViewById(R.id.view);
        adapter = new Adapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setVisibility(View.INVISIBLE);

        try {
            this.fontProvider = new FontProvider(getResources());

            emptyView = findViewById(R.id.emptyView);

            motionView = (MotionView) findViewById(R.id.main_motion_view);
            textEntityEditPanel = findViewById(R.id.main_motion_text_entity_edit_panel);
            textEntityEditPanel.bringToFront();

            motionView.setMotionViewCallback(motionViewCallback);
            textwrite = (ImageButton) findViewById(R.id.textwrite);
            textwrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTextSticker();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        save_Btn = (ImageButton) findViewById(R.id.save_btn);

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "save Button", Toast.LENGTH_SHORT).show();
            }
        });


        paint = (ImageButton) findViewById(R.id.paint);

        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaintEntityColor();
            }
        });

        erase = (ImageButton) findViewById(R.id.erase);

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyView.reset();
                Toast.makeText(getApplicationContext(),"erase Button",Toast.LENGTH_SHORT).show();
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };

                new AlertDialog.Builder(EmptyLayout.this).setTitle("사진앨범").setPositiveButton("앨범선택", albumListener).show();

            }
        });

        initTextEntitiesListeners();
    }
    private void initTextEntitiesListeners() {
        findViewById(R.id.text_entity_font_size_increase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseTextEntitySize();
            }
        });
        findViewById(R.id.text_entity_font_size_decrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseTextEntitySize();
            }
        });
        findViewById(R.id.text_entity_color_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // motionView.changePenColor();
                changeTextEntityColor();
            }
        });
        findViewById(R.id.text_entity_font_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextEntityFont();
            }
        });
        findViewById(R.id.text_entity_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTextEntityEditing();
            }
        });
    }

    private void increaseTextEntitySize() {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            textEntity.getLayer().getFont().increaseSize(TextLayer.Limits.FONT_SIZE_STEP);
            textEntity.updateEntity();
            motionView.invalidate();
        }
    }

    private void decreaseTextEntitySize() {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            textEntity.getLayer().getFont().decreaseSize(TextLayer.Limits.FONT_SIZE_STEP);
            textEntity.updateEntity();
            motionView.invalidate();
        }
    }

    private void changeTextEntityColor() {

        TextEntity textEntity = currentTextEntity();
        if (textEntity == null) {
            return;
        }

        int initialColor = textEntity.getLayer().getFont().getColor();

        ColorPickerDialogBuilder
                .with(EmptyLayout.this)
                .setTitle(R.string.select_color)
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(8) // magic number
                .setPositiveButton(R.string.ok, new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        TextEntity textEntity = currentTextEntity();
                        if (textEntity != null) {
                            textEntity.getLayer().getFont().setColor(selectedColor);
                            textEntity.updateEntity();
                            emptyView.setColor(selectedColor);
                            motionView.invalidate();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void changePaintEntityColor() {

        int initialColor = Color.BLACK;

        ColorPickerDialogBuilder
                .with(EmptyLayout.this)
                .setTitle(R.string.select_color)
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(8) // magic number
                .setPositiveButton(R.string.ok, new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        emptyView.setColor(selectedColor);
                        motionView.invalidate();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void changeTextEntityFont() {
        final List<String> fonts = fontProvider.getFontNames();
        FontsAdapter fontsAdapter = new FontsAdapter(this, fonts, fontProvider);
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_font)
                .setAdapter(fontsAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        TextEntity textEntity = currentTextEntity();
                        if (textEntity != null) {
                            textEntity.getLayer().getFont().setTypeface(fonts.get(which));
                            textEntity.updateEntity();
                            motionView.invalidate();
                        }
                    }
                })
                .show();
    }

    private void startTextEntityEditing() {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            TextEditorDialogFragment fragment0 = TextEditorDialogFragment.getInstance(textEntity.getLayer().getText());
            fragment0.show(getFragmentManager(), TextEditorDialogFragment.class.getName());
        }
    }

    @Nullable
    private TextEntity currentTextEntity() {
        if (motionView != null && motionView.getSelectedEntity() instanceof TextEntity) {
            return ((TextEntity) motionView.getSelectedEntity());
        } else {
            return null;
        }
    }
    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            getMenuInflater().inflate(R.menu.empty_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.main_add_text) {
                addTextSticker();
            }
            return super.onOptionsItemSelected(item);
        }
    */
    protected void addTextSticker() {
        TextLayer textLayer = createTextLayer();
        TextEntity textEntity;
        try{
            textEntity = new TextEntity(textLayer,
                    motionView.getWidth(),
                    motionView.getHeight(),
                    fontProvider);
            motionView.addEntityAndPosition(textEntity);


            // move text sticker up so that its not hidden under keyboard
            PointF center = textEntity.absoluteCenter();
            center.y = center.y * 0.5F;
            textEntity.moveCenterTo(center);
        }catch(Exception e){
            e.printStackTrace();
        }

        // redraw
        motionView.invalidate();

        startTextEntityEditing();
    }


    private TextLayer createTextLayer() {
        TextLayer textLayer = new TextLayer();
        Font font = new Font();

        font.setColor(TextLayer.Limits.INITIAL_FONT_COLOR);
        font.setSize(TextLayer.Limits.INITIAL_FONT_SIZE);
        font.setTypeface(fontProvider.getDefaultFontName());

        textLayer.setFont(font);

        if (BuildConfig.DEBUG) {
            textLayer.setText("Text 입력하세요 :))");
        }

        return textLayer;
    }

    @Override
    public void textChanged(@NonNull String text) {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            TextLayer textLayer = textEntity.getLayer();
            if (!text.equals(textLayer.getText())) {
                textLayer.setText(text);
                textEntity.updateEntity();
                motionView.invalidate();
            }
        }
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if (!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 1080);
                intent.putExtra("outputY", 900);
                intent.putExtra("aspectX", 4);
                intent.putExtra("aspectY", 3);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            }

            case CROP_FROM_IMAGE: {
                if (resultCode != RESULT_OK) {
                    return;
                }

                image = (ImageView) findViewById(R.id.imageView);
                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    photo = extras.getParcelable("data");
                    image.setImageBitmap(photo);

                    storeCropImage(photo, filePath);
                    absolutePath = filePath;

                    Layer layer = new Layer();
                    ImageEntity entity = new ImageEntity(layer, photo, motionView.getWidth(), motionView.getHeight());
                    motionView.addEntityAndPosition(entity);
                    motionView.invalidate();
                    break;
                }

                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

}
