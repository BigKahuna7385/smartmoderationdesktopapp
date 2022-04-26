package SmartModerationDesktopApp.Tutorial;

import java.awt.GraphicsEnvironment;
import javax.swing.ImageIcon;

public class Tutorial extends javax.swing.JFrame {

    private static Tutorial tutorial;

    public Tutorial() {
        initComponents();
        tutorialGif.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tutorial.gif")));
        setIcon();
        centerFrame();
    }

    public static Tutorial getInstance() {
        if (tutorial == null) {
            tutorial = new Tutorial();
        }
        return tutorial;
    }

    private void centerFrame() {
        int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        setLocation(screenWidth / 2 - getBounds().width / 2, screenHeight / 2 - getBounds().height / 2);
    }

    private void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("smartModerationIcon.png"));
        setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tutorialGif = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Quick Guide");
        setAlwaysOnTop(true);
        setIconImage(getIconImage());
        setPreferredSize(new java.awt.Dimension(350, 710));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tutorialGif, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(tutorialGif, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel tutorialGif;
    // End of variables declaration//GEN-END:variables
}
