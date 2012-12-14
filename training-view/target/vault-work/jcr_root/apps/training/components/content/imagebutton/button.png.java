package apps.training.components.content.imagebutton;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.jcr.RepositoryException;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.designer.Style;
import com.day.cq.wcm.commons.AbstractImageServlet;
import com.day.cq.wcm.commons.AbstractImageServlet.ImageContext;
import com.day.cq.commons.ImageHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.image.Font;
import com.day.image.Layer;
import com.day.image.font.AbstractFont;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Link Image button image servlet
 */
public class button_png extends AbstractImageServlet {

    private static final Logger log = LoggerFactory.getLogger(button_png.class);

    protected Layer createLayer(ImageContext c)
            throws RepositoryException, IOException {       
            
        // constants
        final int ALPHA_TRANSPARENT = 0;
        final int ALPHA_SOLID = 255;
        final String INITIAL_COLOR = "#ffffff";
        final int HEIGHT_FIX = 4;
        
        int scale = 1;
        Color bgColor = ImageHelper.parseColor(INITIAL_COLOR, ALPHA_TRANSPARENT);
        Color imgBgColor = ImageHelper.parseColor(INITIAL_COLOR, ALPHA_TRANSPARENT);
        
        // font properties
        Color fgColor = ImageHelper.parseColor(INITIAL_COLOR, ALPHA_TRANSPARENT);
        int fontStyle = ImageHelper.parseFontStyle("bold");
        String fontFace = "Arial";
        String imageText = "Enter text here to generate an image";
        
        // alignment properties
        int paddingY = 5;     // initial values
        int paddingX = 40;  // initial values
        int height = 16;      // initial values
        int width = 200;      // initial values
        double textScale = 0.8; // initial values
        int fontSize = 11;    // initial values
        
        // image size values
        int sliceSize = 5;
        
        // resolver
        PageManager pageManager = c.resolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(c.resource);
        
        // properties and font values
        ValueMap componentProperties = c.properties; 
        
        // padding
        paddingX = new Integer(c.properties.get("button/padding", paddingX)).intValue();
        
        // image text (UPPERCASE)
        imageText = c.properties.get("font/lowercase", "false").equals("true") ? c.properties.get("button/facetext", imageText) : c.properties.get("button/facetext", imageText).toUpperCase();
        fgColor = ImageHelper.parseColor("#" + c.properties.get("font/fontcolor","ffffff"), ALPHA_SOLID);
        
        // font type an size
        fontFace = c.properties.get("font/fontfamilybutton", fontFace);
        fontSize = new Integer(c.properties.get("font/fontsize", "11")).intValue();
        
        // font style
        fontStyle = (c.properties.get("font/fontstylebold", "false").equals("true")?AbstractFont.BOLD:0);
        fontStyle |= (c.properties.get("font/fontstyleitalic", "false").equals("true")?AbstractFont.ITALIC:0);
        fontStyle |= (c.properties.get("font/fontstyleunderline", "false").equals("true")?AbstractFont.UNDERLINE:0);
        
        // get path to button image from design
        Resource resource = c.resource;
        String buttonImg = c.properties.get("image/fileReference","/assets/image/button/imobile-btn-blue.png");
        
        
        // Fetch background resources
        Resource imgResource = resource.getResourceResolver().getResource(buttonImg);
        Asset imageAsset = imgResource.adaptTo(Asset.class);
        Layer bg = ImageHelper.createLayer(c.node.getSession(),imageAsset.getCurrentOriginal().getPath());
              
        
        if (bg == null) {
            bg = new Layer(width, height, imgBgColor);
        }
        
        bg.setBackgroundColor(imgBgColor);
        
        Layer bullet = null; 
        // Fetch bullet resources
        // path to the button bullet
        String bulletImg = c.properties.get("bullet/fileReference",null);
        if (bulletImg!=null){
            Resource bulletResource = resource.getResourceResolver().getResource(bulletImg);
            Asset bulletAsset = bulletResource.adaptTo(Asset.class); 
            bullet = ImageHelper.createLayer(c.node.getSession(),bulletAsset.getCurrentOriginal().getPath());  
        }
        // if there is texto to overlay on a button
        if (imageText.length() > 0) {
            
            // Font to be used, using the ./font/* properties
            Font titleFont = new Font(fontFace, fontSize, fontStyle);
            int titleBase = bg.getHeight();
        
            Rectangle2D rectangleTitle = titleFont.getTextExtent(0, titleBase, 0, 0, imageText, 0, 0, 0);
            
            // Regular text
            Layer text = new Layer((int) (rectangleTitle.getWidth() + (paddingX * 2)), (int) rectangleTitle.getHeight(), bgColor);
            text.setPaint(fgColor);
            text.drawText(paddingX, 0, 0, 0, imageText, titleFont, Font.ALIGN_RIGHT | Font.ALIGN_BASE | Font.TTANTIALIASED, 0, 0);
        
            // if the text rectangle is wider than the image background
            if ( (text.getWidth() ) >= bg.getWidth()){
                
                // difference in size between the image background and the text layer
                double sizeDiff = text.getWidth() - bg.getWidth();
                double sliceNum = (sizeDiff / sliceSize) + 1;
        
                // left side layer
                Layer leftBackground = ImageHelper.createLayer(c.node.getSession(),imageAsset.getCurrentOriginal().getPath());
                
                // right side layer
                Layer rightBackground = ImageHelper.createLayer(c.node.getSession(),imageAsset.getCurrentOriginal().getPath());
                
                // cut the left side from 0,0 (x,y) with size bg.getWidth() / 2,bg.getHeight() (width,height),
                // half the size of the background
                leftBackground.crop(new Rectangle2D.Double(0,0,bg.getWidth() / 2,bg.getHeight()));
                
                // cut the right side from bg.getWidth() / 2,0 (x,y), from half the size across, with size bg.getWidth() / 2,bg.getHeight() (width,height),
                // the other half side
                rightBackground.crop(new Rectangle2D.Double(bg.getWidth() / 2,0,bg.getWidth() / 2,bg.getHeight()));
                
                // cut a slice of the background of sliceSize
                bg.crop(new Rectangle2D.Double(bg.getWidth() / 2- sliceSize,0,sliceSize,bg.getHeight()));
                
                // new size of the left size, the new slices will be merged into this layer
                double resizeValue = ( leftBackground.getWidth() + rightBackground.getWidth() ) + ( bg.getWidth() * sliceNum );
                
                // current with of the left side
                int currentWidth = leftBackground.getWidth();
        
                // resize the left side
                leftBackground.getBounds().setSize((int)resizeValue , bg.getHeight());
                
                for(int slices = 0; slices < sliceNum; slices++){
                    // move current slice to the end of the left image
                    bg.setX(currentWidth);
                    // update the current with of the left image, this is not the
                    // size of the layer but the size of the drawn part
                    currentWidth += bg.getWidth();
                    // merge the bg part of the image to left side
                    leftBackground.merge(bg);
                }
                
                // set the right side of the image ready to be placed at the end
                // of the left image
                rightBackground.setX(leftBackground.getWidth());
            
                // merge the left (with the appended slices) to the right image
                leftBackground.merge(rightBackground);
                
                // maintain bg as the main layer
                bg = leftBackground;
            }
            //crop
            
            Rectangle2D cropText = new Rectangle2D.Double(0,0,(double)text.getWidth(),((double)text.getHeight()/2)+HEIGHT_FIX);
            text.crop(cropText);
            
            
            // padding calculation (center?)
            int textIniPointY = (int)(bg.getHeight() / 2) - (int)((text.getHeight()-HEIGHT_FIX) / 2);
            int textIniPointX = (int)(bg.getWidth() / 2) - (int)(text.getWidth() / 2);
            
            // and merge the layers
            text.setY(textIniPointY);
            text.setX(textIniPointX);
            bg.merge(text);
            
            if (bullet != null) {
                int bulletIniPointY = (int)(bg.getHeight() / 2) - (int)((bullet.getHeight()) / 2);
                int bulletIniPointX = (int)(bg.getWidth() - ((paddingX / 2) + (bullet.getWidth() /2)));
                // and merge the layers
                bullet.setY(bulletIniPointY);
                bullet.setX(bulletIniPointX);
                
                bg.merge(bullet);
        
            }
            
        }
        
        return bg;
    }
}