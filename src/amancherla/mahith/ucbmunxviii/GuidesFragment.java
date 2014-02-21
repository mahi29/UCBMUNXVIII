package amancherla.mahith.ucbmunxviii;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import amancherla.mahith.ucbmunxviii.R;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class GuidesFragment extends Fragment {
	
	public GuidesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);
        downloadPdfContent("ucbmun.org/bgGuides/UNSC_BG.pdf");
        Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
        return rootView;
    }
	 public void downloadPdfContent(String urlToDownload){

         try {
             String fileName="UNSC_BG";
             String fileExtension=".pdf";
//       download pdf file.
            URL url = new URL(urlToDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/mydownload/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, fileName+fileExtension);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();
           System.out.println("--pdf downloaded--ok--"+urlToDownload);
        } catch (Exception e) {
            e.printStackTrace();
        }
	 }
}
