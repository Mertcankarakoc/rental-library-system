package com.library_rental_system.rental.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileResponse extends BaseResponse {
    private Long userId;

    public static UpdateUserProfileResponse userProfileResponse(String message, String status, int statusCode, Long userId) {
        UpdateUserProfileResponse updateUserProfileResponse = new UpdateUserProfileResponse();
        updateUserProfileResponse.setMessage(message);
        updateUserProfileResponse.setStatus(status);
        updateUserProfileResponse.setStatusCode(statusCode);
        updateUserProfileResponse.setUserId(userId);
        return updateUserProfileResponse;

    }
}
