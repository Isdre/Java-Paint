
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Lab6 {

    public static void main(String[] args) {
        new Lab6();
    }

    public Lab6() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Paint");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                TestPane test = new TestPane();
                PaintPane paintPaneLab = test.getPaintPane();
                frame.add(test);
                LabMenu l6 = new LabMenu(frame, paintPaneLab);
                frame.setResizable(false);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private PaintPane paintPane;

        public TestPane() {
            setLayout(new BorderLayout());
            add((paintPane = new PaintPane()));
            add(new ColorsPane(paintPane), BorderLayout.SOUTH);
            add(new SizePane(paintPane), BorderLayout.EAST);
            add(new ShapePane(paintPane), BorderLayout.WEST);
        }

        public PaintPane getPaintPane(){return paintPane;}
    }

    public class LabMenu implements ActionListener {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JFrame _f;
        //[width=713,height=304]
        //[width=713,height=608]
        //[width=1426,height=608]
        PaintPane _paintPane;

        public LabMenu(JFrame f, PaintPane paintPane) {
            _f = f;
            _paintPane = paintPane;
            menuBar = new JMenuBar();
            menu = new JMenu("Project");
            menu.getAccessibleContext().setAccessibleDescription("The only menu in this program");
            menuItem = new JMenuItem("Save");
            menuItem.addActionListener(this);
            menu.add(menuItem);
            menuItem = new JMenuItem("Open");
            menuItem.addActionListener(this);
            menu.add(menuItem);
            submenu = new JMenu("Window size");
            menu.add(submenu);
            menuItem = new JMenuItem("Default");
            menuItem.addActionListener(this);
            submenu.add(menuItem);
            menuItem = new JMenuItem("Medium");
            menuItem.addActionListener(this);
            submenu.add(menuItem);
            menuItem = new JMenuItem("Big");
            menuItem.addActionListener(this);
            submenu.add(menuItem);
            menuItem = new JMenuItem("Info");
            menuItem.addActionListener(this);
            menu.add(menuItem);
            menuItem = new JMenuItem("Quit");
            menuItem.addActionListener(this);
            menu.add(menuItem);
            menuBar.add(menu);
            _f.setJMenuBar(menuBar);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) e.getSource();
            switch (source.getText()) {
                case "Save":
                    BufferedImage bi = _paintPane.background;
                    File outputfile = new File("D:/JetBrains/Projects/Lab6/src/paints/saved.png");
                    try {
                        ImageIO.write(bi, "png", outputfile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Open":
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new File("D:/JetBrains/Projects/Lab6/src/paints/saved.png"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    BufferedImage convertedImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                    convertedImg.getGraphics().drawImage(img, 0, 0, null);
                    _paintPane.background = convertedImg;

                    // parent to głóne okno
                    int ww = _f.getWidth() - _paintPane.getWidth();
                    int hh = _f.getHeight() - _paintPane.getHeight();
                    _f.setSize(convertedImg.getWidth() + ww,convertedImg.getHeight() + hh);
                    _f.setSize(convertedImg.getWidth() + ww,convertedImg.getHeight() + hh);
                    break;
                case "Info":
                    JDialog jd = new JDialog();
                    jd.getContentPane().setLayout(new BorderLayout());
                    jd.getContentPane().add(new JLabel("Author: Gilbert Guszcza"));
                    jd.pack();
                    // okno jest ustawione wzglÄdem okna gĹĂłwnego
                    jd.setLocationRelativeTo(_f);
                    jd.setVisible(true);
                    break;
                case "Quit":
                    _f.setVisible(false);
                    _f.dispose();
                    break;
                //[width=713,height=304]
                //[width=713,height=608]
                //[width=1426,height=608]
                case "Default":
                    _f.setSize(new Dimension(713,304));
                    break;
                case "Medium":
                    _f.setSize(new Dimension(713,608));
                    break;
                case "Big":
                    _f.setSize(new Dimension(1426,608));
                    break;
                default:
                    break;
            }
        }
    }

    public class ColorsPane extends JPanel {

        public ColorsPane(PaintPane paintPane) {
            add(new JButton(new ColorAction(paintPane, "RED", Color.RED)));
            add(new JButton(new ColorAction(paintPane, "GREEN", Color.GREEN)));
            add(new JButton(new ColorAction(paintPane, "BLUE", Color.BLUE)));
            add(new JButton(new ColorAction(paintPane, "YELLOW", Color.YELLOW)));
            add(new JButton(new ColorAction(paintPane, "PINK", Color.PINK)));
            add(new JButton(new ColorAction(paintPane, "CYAN", Color.CYAN)));
            add(new JButton(new ColorAction(paintPane, "GRAY", Color.GRAY)));
            add(new JButton(new ColorAction(paintPane, "DARK_GRAY", Color.DARK_GRAY)));
            add(new JButton(new ColorAction(paintPane, "WHITE", Color.WHITE)));
            add(new JButton(new ColorAction(paintPane, "BLACK", Color.BLACK)));
        }

        public class ColorAction extends AbstractAction {

            private PaintPane paintPane;
            private Color color;

            private ColorAction(PaintPane paintPane, String name, Color color) {
                putValue(NAME, name);
                this.paintPane = paintPane;
                this.color = color;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                paintPane.setForeground(color);
            }
        }
    }

    public class SizePane extends JPanel {
        public SizePane(PaintPane paintPane) {
            JSlider size = new JSlider(JSlider.VERTICAL, 1, 40, 10);
            size.addChangeListener(new SizeAction(paintPane));
            add(size);
        }

        public class SizeAction implements ChangeListener {

            private PaintPane paintPane;

            private SizeAction(PaintPane paintPane) {
                this.paintPane = paintPane;
            }

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                this.paintPane.widthDot = source.getValue();
                this.paintPane.heightDot = source.getValue();
            }
        }
    }

    public class ShapePane extends JPanel {

        public ShapePane(PaintPane paintPane) {
            add(new JButton(new ShapeAction(paintPane, "Circle",0)));
            add(new JButton(new ShapeAction(paintPane, "Rect",1)));
            add(new JButton(new ShapeAction(paintPane, "Half-Circle",2)));
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        }

        public class ShapeAction extends AbstractAction {

            private PaintPane paintPane;
            private int id;

            private ShapeAction(PaintPane paintPane, String name,int id) {
                putValue(NAME, name);
                this.paintPane = paintPane;
                this.id = id;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                paintPane.shapeDot = id;
            }
        }
    }

    public class PaintPane extends JPanel {

        private BufferedImage background;
        public int widthDot = 10;
        public int heightDot = 10;

        public int shapeDot = 0;
        //Oval

        public PaintPane() {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            MouseAdapter handler = new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    drawDot(e.getPoint());
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    drawDot(e.getPoint());
                }

            };
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }

        protected void drawDot(Point p) {
            if (background == null) {
                updateBuffer();;
            }

            if (background != null) {
                Graphics2D g2d = background.createGraphics();
                g2d.setColor(getForeground());
                switch (shapeDot) {
                    case 0:
                        g2d.fillOval(p.x - widthDot / 2, p.y - heightDot / 2, widthDot, heightDot);
                        break;
                    case 1:
                        g2d.fillRect(p.x - widthDot / 2, p.y - heightDot / 2, widthDot, heightDot);
                    case 2:
                        g2d.fillArc(p.x - widthDot / 2, p.y - heightDot / 2, widthDot, widthDot,0,180);
                        break;
                    default:break;
                }
                g2d.dispose();
            }
            repaint();
        }

        @Override
        public void invalidate() {
            super.invalidate();
            updateBuffer();
        }

        protected void updateBuffer() {

            if (getWidth() > 0 && getHeight() > 0) {
                BufferedImage newBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = newBuffer.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                if (background != null) {
                    g2d.drawImage(background, 0, 0, this);
                }
                g2d.dispose();
                background = newBuffer;
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (background == null) {
                updateBuffer();
            }
            g2d.drawImage(background, 0, 0, this);
            g2d.dispose();
        }
    }
}