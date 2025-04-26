package com.anirudh.dto;
import com.anirudh.entity.ComplaintCategory;
import com.anirudh.entity.ComplaintStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintDTO {
    private String name;
    private String phoneNumber;
    private String aadhaarNumber;
    private String address;
    private ComplaintCategory category;
    private String description;
    private ComplaintStatus status;
}