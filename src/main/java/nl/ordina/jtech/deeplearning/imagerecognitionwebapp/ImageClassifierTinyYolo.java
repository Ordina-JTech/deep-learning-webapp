//package nl.ordina.jtech.deeplearning.imagerecognitionwebapp;
//import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2RGB;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//import org.datavec.image.loader.NativeImageLoader;
//import org.datavec.image.transform.ColorConversionTransform;
//import org.deeplearning4j.nn.graph.ComputationGraph;
//import org.deeplearning4j.nn.layers.objdetect.DetectedObject;
//import org.deeplearning4j.zoo.ZooModel;
//import org.deeplearning4j.zoo.model.TinyYOLO;
//import org.deeplearning4j.zoo.util.ClassPrediction;
//import org.deeplearning4j.zoo.util.darknet.VOCLabels;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ImageClassifierTinyYolo {
//    private static final int HEIGHT = 224;
//    private static final int WIDTH = 224;
//    private static final int CHANNELS = 3;
//    private ZooModel zooModel;
//    private ComputationGraph computationGraph;
//    private NativeImageLoader loader;
//
//    String classify(InputStream inputStream) {
//
//        final Logger logger = LoggerFactory.getLogger(ImageClassifierTinyYolo.class);
//
//        INDArray image = null;
//
//        String outputString = "Output: ";
//
//        // set up input and feedforward
//        try {
//            image = loader.asMatrix(inputStream);
//            final ImagePreProcessingScaler scaler = new ImagePreProcessingScaler(0, 1);
//            scaler.transform(image);
//            INDArray outputs = computationGraph.outputSingle(image);
//            org.deeplearning4j.nn.layers.objdetect.Yolo2OutputLayer yout =x (org.deeplearning4j.nn.layers.objdetect.Yolo2OutputLayer) computationGraph.getOutputLayer(0);
//            List<DetectedObject> objs = yout.getPredictedObjects(outputs, 0.6f);
//
//            // check output labels of result
//            final VOCLabels labels = new VOCLabels();
//            for (DetectedObject obj : objs) {
//                ClassPrediction classPrediction = labels.decodePredictions(obj.getClassPredictions(), 1).get(0).get(0);
//                outputString = outputString + classPrediction.getLabel() + ": " + classPrediction.getProbability() + " \n";
//
//            }
//
//        } catch (IOException e) {
//            logger.info(e.getMessage());
//            e.printStackTrace();
//        }
//        return outputString;
//
//    }
//
//    void init() {
//        try {
//            zooModel = new TinyYOLO(1, 123); //num labels doesn't matter since we're getting pretrained imagenet
//            computationGraph = (ComputationGraph) zooModel.initPretrained();
//            loader = new NativeImageLoader(416, 416, 3, new ColorConversionTransform(COLOR_BGR2RGB));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
