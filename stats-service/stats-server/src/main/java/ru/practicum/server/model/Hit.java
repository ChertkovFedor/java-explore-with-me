package ru.practicum.server.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "HITS")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "APP", nullable = false, length = 64)
    private String app;
    @Column(name = "URI", nullable = false)
    private String uri;
    @Column(name = "IP", nullable = false, length = 32)
    private String ip;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;
}