package org.dyzeng.imagelib.web;

import org.dyzeng.imagelib.service.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UpdateController {
    @Autowired
    private ImageUtils imageUtils;

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String update(RedirectAttributes redirectAttributes) {
        try {
            imageUtils.updateLibrary();
            redirectAttributes.addFlashAttribute("message", "Update succeeded.");
            return "redirect:/search";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Update failed.");
        }
        return "redirect:/search";
    }
}
