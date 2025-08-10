package br.com.erudio.data.dto;

import java.util.Objects;

public class EmailDTO {

    private String to;
    private String body;
    private String subject;

    public EmailDTO() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmailDTO emailDTO = (EmailDTO) o;
        return Objects.equals(to, emailDTO.to) && Objects.equals(body, emailDTO.body) && Objects.equals(subject, emailDTO.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, body, subject);
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "to='" + to + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
