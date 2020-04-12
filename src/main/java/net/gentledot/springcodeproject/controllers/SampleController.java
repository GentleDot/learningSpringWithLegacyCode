package net.gentledot.springcodeproject.controllers;

import net.gentledot.springcodeproject.model.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("web")
public class SampleController {
    private static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping(value = "doA")
    public void doA() {
        log.info("doA called!..........................");
    }

    @RequestMapping(value = "doB")
    public void doB() {
        log.info("doB called!..........................");
    }

    @RequestMapping(value = "doC")
    public String doC(@ModelAttribute(value = "msg") String msg) {
        log.info("doC called!..........................");

        return "result";
    }

    @RequestMapping(value = "doD")
    public String doD(Model model) {
        log.info("doD called!..........................");

        ProductVO tempProduct = new ProductVO("tempProduct", 10000);

        model.addAttribute("product", tempProduct);

        return "productDetail";

    }

    @RequestMapping(value = "doE")
    public String doE(RedirectAttributes redirectAttributes) {
        log.info("doE called but redirect to /doF...............");

        redirectAttributes.addFlashAttribute("msg", "this is the message!! with redirected.");
        return "redirect:/web/doF";
    }

    @RequestMapping(value = "doF")
    public void doF(String msg) {
        log.info("doF Called!................ with message : " + msg);
    }

    @RequestMapping("/doJSON")
    public @ResponseBody ProductVO doJson(){
        return new ProductVO("tempProduct", 2000);
    }


}
