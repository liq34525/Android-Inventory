package comp3350.ei.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import comp3350.ei.R;

import android.content.Context;
import java.io.File;
import android.content.res.AssetManager;
import comp3350.ei.application.Main;
import comp3350.ei.presentation.inventory.InventoryActivity;
import comp3350.ei.presentation.orders.OrdersActivity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        copyDatabaseToDevice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void onClickBtnInventory(View view) {
        Intent inventory = new Intent(HomeActivity.this, InventoryActivity.class);
        HomeActivity.this.startActivity(inventory);
    }

    public void onClickBtnOrders(View view) {
        Intent orders = new Intent(HomeActivity.this, OrdersActivity.class);
        HomeActivity.this.startActivity(orders);
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            //load in all asset file
            assetNames = assetManager.list(DB_PATH);

            if (assetNames != null) {
                for (int i = 0; i < assetNames.length; i++) {
                    assetNames[i] = DB_PATH + "/" + assetNames[i];
                }

                copyAssetsToDirectory(assetNames, dataDirectory);

                Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());
            }
        } catch (final IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }


    private void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            //check if database file already exit?
            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }


}
