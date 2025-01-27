package com.library_rental_system.rental.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    public String message = "";
    public String status = "";
    public int statusCode = 0;
    public String errorCode = "";
    public String messageCode = "";
}
