package tn.pingghhost.chindo.beta;

import java.io.IOException;
import java.util.List;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
    	Video<MBFImage> video;
        video = new VideoCapture(620, 340);
VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
display.addVideoListener(
  new VideoDisplayListener<MBFImage>() {
    @Override
    public void beforeUpdate(MBFImage frame) {
        FaceDetector<KEDetectedFace,FImage> fd = new FKEFaceDetector();
        List<KEDetectedFace> faces = fd.detectFaces( Transforms.calculateIntensity( frame ) );
        for( DetectedFace face : faces ) {
        frame.drawShape(face.getBounds(), RGBColour.GREEN);
          
        }
          for (KEDetectedFace faceK : faces)
        {
            FacialKeypoint[] k = faceK.getKeypoints();
            for (int i = 0 ; i<k.length;i++)
            {
                Point2dImpl p = k[i].position;
                Rectangle rec = faceK.getBounds();
                float minx = (float) rec.minX();
                float miny = (float) rec.minY();
                p.translate(minx, miny);
                frame.drawPoint(p, RGBColour.RED, 5);
            }
            
        }
       

    }

    @Override
    public void afterUpdate(VideoDisplay<MBFImage> display) {
    }
  });


    }
}
