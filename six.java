import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class six extends JFrame {

    private JTextField urlTextField;
    private JButton downloadButton;
    private JTextArea statusTextArea;
    private ExecutorService executorService;

    public six() {
        setTitle("Image Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        executorService = Executors.newFixedThreadPool(5); // Adjust the pool size as needed
    }

    private void initComponents() {
        urlTextField = new JTextField(30);
        downloadButton = new JButton("Download");
        statusTextArea = new JTextArea(20, 40);

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = urlTextField.getText().trim();
                if (!url.isEmpty()) {
                    downloadImages(url);
                }
            }
        });

        setLayout(new FlowLayout());
        add(new JLabel("Enter URL: "));
        add(urlTextField);
        add(downloadButton);
        add(new JScrollPane(statusTextArea));
    }

    private void downloadImages(String url) {
        SwingWorker<Void, ImageDownloadStatus> worker = new SwingWorker<Void, ImageDownloadStatus>() {
            @Override
            protected Void doInBackground() {
                try {
                    List<URL> imageUrls = ImageUtils.extractImageUrls(new URL(url));
                    CountDownLatch latch = new CountDownLatch(imageUrls.size());

                    for (URL imageUrl : imageUrls) {
                        executorService.execute(() -> {
                            try {
                                ImageUtils.downloadImage(imageUrl);
                                publish(new ImageDownloadStatus(imageUrl, true, 100));
                            } catch (IOException e) {
                                publish(new ImageDownloadStatus(imageUrl, false, 0));
                            } finally {
                                latch.countDown();
                            }
                        });
                    }

                    latch.await();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void process(List<ImageDownloadStatus> chunks) {
                for (ImageDownloadStatus status : chunks) {
                    updateStatus(status);
                }
            }

            @Override
            protected void done() {
                executorService.shutdown();
            }
        };

        worker.execute();
    }

    private void updateStatus(ImageDownloadStatus status) {
        String message = status.isSuccess()
                ? "Downloaded: " + status.getUrl() + " - " + status.getPercentage() + "%\n"
                : "Failed to download: " + status.getUrl() + "\n";

        statusTextArea.append(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new six());
    }
}

class ImageDownloadStatus {
    private final URL url;
    private final boolean success;
    private final int percentage;

    public ImageDownloadStatus(URL url, boolean success, int percentage) {
        this.url = url;
        this.success = success;
        this.percentage = percentage;
    }

    public URL getUrl() {
        return url;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPercentage() {
        return percentage;
    }
}

class ImageUtils {
    public static List<URL> extractImageUrls(URL url) throws IOException {
        // Implement logic to extract image URLs from the given URL
        // For simplicity, returning an empty list here
        return List.of();
    }

    public static void downloadImage(URL imageUrl) throws IOException {
        // Implement logic to download the image from the given URL
    }
}
