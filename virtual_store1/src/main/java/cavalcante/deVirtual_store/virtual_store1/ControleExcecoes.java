package cavalcante.deVirtual_store.virtual_store1;

import cavalcante.deVirtual_store.virtual_store1.dtos.ObjetoErroDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe responsável por interceptar e tratar exceções de forma centralizada em toda a aplicação.
 * Utiliza as anotações @ControllerAdvice e @RestControllerAdvice para capturar exceções de
 * controladores REST e fornecer uma resposta personalizada ao cliente.
 */

@RestControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

    /**
     * Método que trata exceções de forma genérica, incluindo Exception, RuntimeException e Throwable.
     * Essa abordagem captura qualquer exceção não tratada explicitamente nos controladores.
     *
     * @param ex A exceção lançada.
     * @param body O corpo da resposta atual, geralmente nulo neste contexto.
     * @param headers Os cabeçalhos HTTP a serem retornados.
     * @param status O status HTTP correspondente à exceção.
     * @param request O contexto da requisição atual.
     * @return Um ResponseEntity contendo um DTO com as informações do erro e o status HTTP apropriado.
     */
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    )

    {
        // Cria uma instância do DTO de erro para encapsular os detalhes da exceção.
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        // StringBuilder usado para construir a mensagem de erro de forma dinâmica.
        StringBuilder msg = new StringBuilder();

        // Verifica se a exceção é do tipo MethodArgumentNotValidException.
        // Esse tipo de exceção ocorre quando há validações falhas em métodos que recebem dados do cliente.
        if (ex instanceof MethodArgumentNotValidException) {
            // Obtém a lista de erros de validação.
            List<ObjectError> errors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            // Concatena as mensagens de erro para construir uma mensagem detalhada.
            for (ObjectError objectError : errors) {
                msg.append(objectError.getDefaultMessage()).append("\n");
            }
        } else {
            // Caso não seja uma exceção de validação, utiliza a mensagem genérica da exceção.
            msg.append(ex.getMessage());
        }

        // Preenche o DTO de erro com as informações processadas.
        objetoErroDTO.setError(msg.toString()); // Define a mensagem detalhada do erro.
        objetoErroDTO.setCode(String.valueOf(statusCode.value())); // Define o código HTTP como string.

        // Retorna a resposta HTTP com o DTO de erro e o status apropriado.
        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


     // Captura erro na parte de banco
    @ExceptionHandler ({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex){

        // Cria uma instância do DTO de erro para encapsular os detalhes da exceção.
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        // StringBuilder usado para construir a mensagem de erro de forma dinâmica.
        StringBuilder msg = new StringBuilder();

        if (ex instanceof SQLException){
            msg.append(((SQLException) ex).getCause().getCause().getMessage());
        }else

        {
            msg.append(ex.getMessage());
        }

        // Preenche o DTO de erro com as informações processadas.
        objetoErroDTO.setError(msg.toString()); // Define a mensagem detalhada do erro.
        objetoErroDTO.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())); // Define o código HTTP como string.

        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }




}
