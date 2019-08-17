package app.circle.foodmood.controller.webController

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView


@ControllerAdvice
class ExceptionWebController {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, model: Model): ModelAndView {
        val model = ModelAndView()

        return model

    }
}
