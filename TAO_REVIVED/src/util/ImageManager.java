package util;

import java.awt.image.ImageProducer;
import java.util.Iterator;
import effects.Paralyze;
import effects.Poison;
import effects.Focus;
import effects.Barrier;
import effects.Effect;
import java.util.Set;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.awt.Color;

public class ImageManager
{
    private static final char[] keys;
    private static final String[] files;
    public static final Color BACKGROUND;
    public static final float DEFAULT_HUE1 = 0.0f;
    public static final float DEFAULT_HUE2 = 0.67f;
    private static final int MAX_WIDTH = 53;
    private static final int MAX_HEIGHT = 50;
    private static final int HP_BAR_WIDTH = 3;
    private static final int bg1Color = -16777216;
    private static final int bg2Color = -12566464;
    private static final int nBgColor;
    private static final int hpbarColor;
    private static final int hpbarColor2;
    private static final int hpbgColor;
    private static final int barrierColor;
    private static final int focusColor;
    private static final int poisonColor;
    private static final int paralysisColor;
    private static final ImageManager instance;
    private HashMap<Character, BufferedImage> mapPlayer1;
    private HashMap<Character, BufferedImage> mapPlayer2;
    private HashMap<Character, BufferedImage> mapOthers;
    private float hue1;
    private float hue2;

    static {
        keys = new char[] { 'C', 'K', 'S', 'L', 'b', 'W', 'E', 'A', 'P', 'D', 'M', 'G', 'f', 's', 'T', 'Z', 'B', 'p', 'F' };
        files = new String[] { "cleric", "knight", "scout", "lightningward", "barrierward", "darkmagicwitch", "enchantress", "assassin", "pyromancer", "dragonspeakermage", "mudgolem", "golemambusher", "frostgolem", "stonegolem", "dragontyrant", "berserker", "beastrider", "poisonwisp", "furgon" };
        BACKGROUND = new Color(238, 238, 238);
        nBgColor = new Color(255, 255, 255).getRGB();
        hpbarColor = Color.green.getRGB();
        hpbarColor2 = Color.red.getRGB();
        hpbgColor = Color.black.getRGB();
        barrierColor = Color.red.darker().getRGB();
        focusColor = Color.darkGray.getRGB();
        poisonColor = Color.yellow.darker().darker().getRGB();
        paralysisColor = Color.CYAN.darker().getRGB();
        instance = new ImageManager();
    }

    public static ImageManager getInstance() {
        return ImageManager.instance;
    }

    private ImageManager() {
        this.mapOthers = new HashMap<Character, BufferedImage>();
        this.mapPlayer1 = new HashMap<Character, BufferedImage>();
        this.mapPlayer2 = new HashMap<Character, BufferedImage>();
        this.hue1 = 0.0f;
        this.hue2 = 0.67f;
        this.initialize();
    }

    private void initialize() {
        this.mapOthers.clear();
        this.mapPlayer1.clear();
        this.mapPlayer2.clear();
        for (int i = 0; i < ImageManager.keys.length; ++i) {
            float colorToChange;
            if (i < 10) {
                colorToChange = 0.63f;
            }
            else if (i < 14) {
                colorToChange = 0.0f;
            }
            else {
                colorToChange = 0.21f;
            }
            final char key = ImageManager.keys[i];
            final String file = ImageManager.files[i];
            BufferedImage img1 = null;
            BufferedImage img2 = null;
            try {
                img1 = ImageIO.read(this.getClass().getResource("/images/" + file + ".bmp"));
                img2 = ImageIO.read(this.getClass().getResource("/images/" + file + ".bmp"));
            }
            catch (IOException e) {
                throw new IllegalStateException("Can't get image for " + file);
            }
            if (img1 == null || img2 == null) {
                throw new IllegalStateException("Can't get image for " + file);
            }
            for (int x = 0; x < img1.getWidth(); ++x) {
                for (int y = 0; y < img1.getHeight(); ++y) {
                    final Color c = new Color(img1.getRGB(x, y));
                    final float[] hsb = new float[3];
                    Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
                    if (c.getRGB() == -16777216 || (c.getRGB() == -12566464 && !file.equals("dragonspeakermage"))) {
                        img1.setRGB(x, y, ImageManager.nBgColor);
                        img2.setRGB(x, y, ImageManager.nBgColor);
                    }
                    else if (Math.abs(hsb[0] - colorToChange) < 0.1) {
                        img1.setRGB(x, y, Color.HSBtoRGB(this.hue1, hsb[1], hsb[2]));
                        img2.setRGB(x, y, Color.HSBtoRGB(this.hue2, hsb[1], hsb[2]));
                    }
                }
            }
            this.mapPlayer1.put(key, img1);
            this.mapPlayer2.put(key, img2);
        }
        BufferedImage img3 = null;
        try {
            img3 = ImageIO.read(this.getClass().getResource("/images/shrub.bmp"));
        }
        catch (IOException e2) {
            throw new IllegalStateException("Can't get image for shrub");
        }
        if (img3 == null) {
            throw new IllegalStateException("Can't get image for shrub");
        }
        this.mapOthers.put('*', img3);
        for (int x2 = 0; x2 < img3.getWidth(); ++x2) {
            for (int y2 = 0; y2 < img3.getHeight(); ++y2) {
                final Color c2 = new Color(img3.getRGB(x2, y2));
                if (c2.getRGB() == -16777216 || c2.getRGB() == -12566464) {
                    img3.setRGB(x2, y2, ImageManager.nBgColor);
                }
            }
        }
    }

    public ImageIcon getImageIcon(final char unit, final boolean player1, final Direction dir) {
        final Image scaled = this.getImage(unit, player1, dir).getScaledInstance(53, 50, 4);
        return new ImageIcon(scaled);
    }

    public ImageIcon getImageIcon(final char unit, final boolean player1, final Direction dir, final float hp, final Set<Effect> effects) {
        final BufferedImage bi = this.getImage(unit, player1, dir, hp, effects);
        final Image scaled = bi.getScaledInstance(53, 50, 4);
        return new ImageIcon(scaled);
    }

    public BufferedImage getImage(final char unit, final boolean player1, final Direction dir) {
        BufferedImage image;
        if (!this.mapOthers.containsKey(unit)) {
            if (player1) {
                image = this.mapPlayer1.get(unit);
            }
            else {
                image = this.mapPlayer2.get(unit);
            }
        }
        else {
            image = this.mapOthers.get(unit);
        }
        int realHPBarWidth;
        if (1.0f > image.getWidth() / image.getHeight()) {
            realHPBarWidth = Math.round(3.0f * image.getHeight() / 50.0f);
        }
        else {
            realHPBarWidth = Math.round(3.0f * image.getWidth() / 50.0f);
        }
        final double r1 = 0.9433962264150944;
        final double r2 = image.getHeight() / (image.getWidth() + realHPBarWidth);
        int width = image.getWidth() + realHPBarWidth;
        int height = image.getHeight();
        if (0.9433962264150944 > r2) {
            height = (int)(width * 0.9433962264150944);
        }
        else {
            width = (int)(height / 0.9433962264150944);
        }
        final BufferedImage unscaled = new BufferedImage(width, height, 2);
        final int dx = (width - image.getWidth() + realHPBarWidth) / 2;
        final int dy = (height - image.getHeight()) / 2;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                unscaled.setRGB(x, y, ImageManager.nBgColor);
                if (x >= dx && x < dx + image.getWidth() && y >= dy && y < dy + image.getHeight()) {
                    unscaled.setRGB(x, y, image.getRGB(x - dx, y - dy));
                }
                if (unscaled.getRGB(x, y) == ImageManager.nBgColor && dir != null) {
                    boolean drawArrow = false;
                    switch (dir.intValue()) {
                        case 0: {
                            drawArrow = this.upArrowFunction(x - realHPBarWidth, y, width - realHPBarWidth, height);
                            break;
                        }
                        case 1: {
                            drawArrow = this.rightArrowFunction(x - realHPBarWidth, y, width - realHPBarWidth, height);
                            break;
                        }
                        case 2: {
                            drawArrow = this.downArrowFunction(x - realHPBarWidth, y, width - realHPBarWidth, height);
                            break;
                        }
                        case 3: {
                            drawArrow = this.leftArrowFunction(x - realHPBarWidth, y, width - realHPBarWidth, height);
                            break;
                        }
                    }
                    if (drawArrow) {
                        if (player1) {
                            unscaled.setRGB(x, y, Color.HSBtoRGB(this.hue1, 1.0f, 1.0f));
                        }
                        else {
                            unscaled.setRGB(x, y, Color.HSBtoRGB(this.hue2, 1.0f, 1.0f));
                        }
                    }
                    else {
                        unscaled.setRGB(x, y, new Color(255, 255, 255, 0).getRGB());
                    }
                }
                else if (unscaled.getRGB(x, y) == ImageManager.nBgColor) {
                    unscaled.setRGB(x, y, new Color(255, 255, 255, 0).getRGB());
                }
            }
        }
        return unscaled;
    }

    public BufferedImage getImage(final char unit, final boolean player1, final Direction dir, final float hp, final Set<Effect> effects) {
        final BufferedImage unscaled = this.getImage(unit, player1, dir);

        BufferedImage image;
        if (!this.mapOthers.containsKey(unit)) {
            if (player1) {
                image = this.mapPlayer1.get(unit);
            }
            else {
                image = this.mapPlayer2.get(unit);
            }
        }
        else {
            image = this.mapOthers.get(unit);
        }
        int barrierWidth = 100;
        int trianglesize = 10;
        int realHPBarWidth;
        if (1.0f > image.getWidth() / image.getHeight()) {
            realHPBarWidth = Math.round(3.0f * image.getHeight() / 50.0f);
            barrierWidth = Math.round(barrierWidth * image.getHeight() / 50.0f);
            trianglesize = Math.round(trianglesize * image.getHeight() / 50.0f);
        }
        else {
            realHPBarWidth = Math.round(3.0f * image.getWidth() / 50.0f);
            barrierWidth = Math.round(barrierWidth * image.getWidth() / 50.0f);
            trianglesize = Math.round(trianglesize * image.getWidth() / 50.0f);
        }
        for (final Effect e : effects) {
            if (e instanceof Barrier) {
                for (int r = (unscaled.getWidth() - realHPBarWidth) / 2, x = 0; x < 2 * r; ++x) {
                    for (int y = 0; y < 2 * r; ++y) {
                        if (Math.abs(x * x + y * y + r * r - 2 * (x + y) * r + barrierWidth) < barrierWidth) {
                            if(findUnitName(unit).equals("furgon")) { //74x69 pixels furgon
                                int tempX = x;   //clamps x and y to unscaled max height and width
                                int tempY = y;
                                if(x > unscaled.getWidth() - 1) {
                                    tempX = unscaled.getWidth() - 1;
                                }
                                if(y > unscaled.getHeight() - 1) {
                                    tempY = unscaled.getHeight() - 1;
                                }
                                unscaled.setRGB(tempX + realHPBarWidth, tempY, ImageManager.barrierColor);
                            }
                            else {
                                unscaled.setRGB(x + realHPBarWidth, y, ImageManager.barrierColor);
                            }
                        }
                    }
                }
            }
            if (e instanceof Focus) {
                for (int x2 = 0; x2 < trianglesize; ++x2) {
                    for (int y2 = 0; y2 < trianglesize - x2; ++y2) {
                        unscaled.setRGB(x2 + realHPBarWidth, y2, ImageManager.focusColor);
                        unscaled.setRGB(x2 + realHPBarWidth, unscaled.getHeight() - y2 - 1, ImageManager.focusColor);
                        unscaled.setRGB(unscaled.getWidth() - x2 - 1, y2, ImageManager.focusColor);
                        unscaled.setRGB(unscaled.getWidth() - x2 - 1, unscaled.getHeight() - y2 - 1, ImageManager.focusColor);
                    }
                }
            }
            if (e instanceof Poison) {
                for (int x2 = 0; x2 < trianglesize; ++x2) {
                    for (int y2 = 0; y2 < trianglesize - x2; ++y2) {
                        unscaled.setRGB(x2 + realHPBarWidth, y2, ImageManager.poisonColor);
                        unscaled.setRGB(unscaled.getWidth() - x2 - 1, unscaled.getHeight() - y2 - 1, ImageManager.poisonColor);
                    }
                }
            }
            if (e instanceof Paralyze) {
                for (int x2 = 0; x2 < trianglesize; ++x2) {
                    for (int y2 = 0; y2 < trianglesize - x2; ++y2) {
                        unscaled.setRGB(x2 + realHPBarWidth, unscaled.getHeight() - y2 - 1, ImageManager.paralysisColor);
                        unscaled.setRGB(unscaled.getWidth() - x2 - 1, y2, ImageManager.paralysisColor);
                    }
                }
            }
        }
        final int cutoff = Math.round(unscaled.getHeight() * (1.0f - hp));
        final Color c1 = new Color(ImageManager.hpbarColor);
        final Color c2 = new Color(ImageManager.hpbarColor2);
        final float[] a1 = new float[3];
        final float[] a2 = new float[3];
        Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), a1);
        Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), a2);
        final float hue = a2[0] + (a1[0] - a2[0]) * hp;
        final int color = Color.getHSBColor(hue, a1[1], a1[2]).getRGB();
        for (int x3 = 0; x3 < realHPBarWidth; ++x3) {
            for (int y3 = 0; y3 < cutoff; ++y3) {
                unscaled.setRGB(x3, y3, ImageManager.hpbgColor);
            }
            for (int y3 = cutoff; y3 < unscaled.getHeight(); ++y3) {
                unscaled.setRGB(x3, y3, color);
            }
        }
        return unscaled;
    }

    private boolean leftArrowFunction(final int x, final int y, final int width, final int height) {
        return 2 * y * width + x * height >= width * height && 2 * y * width - x * height <= width * height;
    }

    private boolean rightArrowFunction(final int x, final int y, final int width, final int height) {
        return 2 * y * width - x * height >= 0 && 2 * y * width + x * height <= 2 * width * height;
    }

    private boolean upArrowFunction(final int x, final int y, final int width, final int height) {
        return y * width + 2 * x * height >= width * height && 2 * x * height - y * width <= width * height;
    }

    private boolean downArrowFunction(final int x, final int y, final int width, final int height) {
        return 2 * x * height - width * y >= 0 && 2 * x * height + y * width <= 2 * width * height;
    }

    private String findUnitName(char unitChar) {
        for(int i = 0; i < keys.length; i++) {
            if(keys[i] == unitChar) {
                return files[i];
            }
        }
        return null;
    }
}
