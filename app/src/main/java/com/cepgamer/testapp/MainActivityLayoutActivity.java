package com.cepgamer.testapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivityLayoutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private static final String IMAGE_VIEW_BITMAP = "image_view_bitmap";
    private Button _downloadButton;
    private EditText _editText;
    private ImageView _imageView;
    private TextView _errorText;
    private static GetWebImageTask _getPictureTask;

    private class GetImageCallback implements GetWebImageTask.OnDownloadedCallback
    {
        @Override
        public void result(Bitmap result)
        {
            if (result != null)
            {
                _imageView.setImageBitmap(result);
                _errorText.setVisibility(View.GONE);
            }
            else
            {
                _imageView.setImageBitmap(null);
                _errorText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _editText = (EditText) findViewById(R.id.image_link);
        _imageView = (ImageView) findViewById(R.id.image);
        _errorText = (TextView) findViewById(R.id.error_text);

        _downloadButton = (Button) findViewById(R.id.download_button);
        _downloadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    final URL imageUrl = new URL(_editText.getText().toString());

                    _getPictureTask = new GetWebImageTask(new GetImageCallback());

                    _getPictureTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageUrl);

                    _imageView.setImageBitmap(null);
                }
                catch (final MalformedURLException e)
                {
                    Toast.makeText(MainActivityLayoutActivity.this, "URL you entered is invalid", Toast.LENGTH_LONG).show();
                    _errorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            // Handle the camera action
        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle stateToSave)
    {
        super.onSaveInstanceState(stateToSave);
        if (_imageView.getDrawable() instanceof BitmapDrawable)
        {
            stateToSave.putParcelable(IMAGE_VIEW_BITMAP, ((BitmapDrawable) _imageView.getDrawable()).getBitmap());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        final Bitmap image = savedInstanceState.getParcelable(IMAGE_VIEW_BITMAP);
        if (image != null)
        {
            _imageView.setImageBitmap(image);
        }

        _getPictureTask.setOnDownloadedCallback(new GetImageCallback());
    }
}
