package fr.pereiraesteban.kata_api;

import fr.pereiraesteban.kata_api.response.ErrorResponse;
import fr.pereiraesteban.kata_api.response.KataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/kata/")
public class Api {

  private final KataService kataService;

  public Api(KataService kataService) {
    this.kataService = Objects.requireNonNull(kataService);
  }

  @GetMapping
  @Operation(summary = "Perform the kata", operationId = "performKata", tags = "Kata")
  @ApiResponse(responseCode = "200", content = @Content(
      mediaType = "application/json",
      schema = @Schema(implementation = KataResponse.class)
    )
  )
  public ResponseEntity<KataResponse> kata(@RequestParam @NotNull Integer input) {
    if (input < 0 || input > 100) {
      throw new InvalidInputRangeException(input + " is not in [0, 100]");
    }
    var response = kataService.apply(input);
    return ResponseEntity.ok().body(new KataResponse(response));
  }

  @ExceptionHandler(InvalidInputRangeException.class)
  public ResponseEntity<ErrorResponse> handleMissingParams(InvalidInputRangeException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
  }
}
