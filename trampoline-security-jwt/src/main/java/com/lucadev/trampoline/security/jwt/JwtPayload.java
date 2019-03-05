package com.lucadev.trampoline.security.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Representation of the data inside the JWT
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class JwtPayload implements Serializable {

    /**
     * Raw token data
     */
    private String rawToken;
    /**
     * Subject ID(UUID)
     */
    private UUID subject;
    /**
     * Subject username
     */
    private String username;
    /**
     * Subject email
     */
    private String email;
    /**
     * Subject roles
     */
    private List<String> roles;
    /**
     * Token issue date
     */
    private Date issuedDate;
    /**
     * Token expiry date
     */
    private Date expirationDate;
    /**
     * Token flag to see if this token is used to impersonate someone
     */
    private boolean impersonateMode;
    /**
     * The UUID of the user who started the impersonate
     */
    private UUID impersonateInitiatorId;
    /**
     * Can we ignore expirydate?
     */
    private boolean ignorableExpiration;

}