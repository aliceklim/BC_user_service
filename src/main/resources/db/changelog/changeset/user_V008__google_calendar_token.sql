CREATE TABLE google_credentials (
    id                          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    client_id                   varchar(128) UNIQUE NOT NULL,
    client_email                varchar(128) UNIQUE NOT NULL,
    project_id                  varchar(32) UNIQUE NOT NULL,
    auth_uri                    varchar(64) UNIQUE NOT NULL,
    token_uri                   varchar(64) UNIQUE NOT NULL,
    auth_provider_x509_cert_url varchar(64) UNIQUE NOT NULL,
    client_secret               varchar(64) UNIQUE NOT NULL,
    redirect_uri                varchar(64) UNIQUE NOT NULL,
    javascript_origin           varchar(64) UNIQUE NOT NULL
);

INSERT INTO google_credentials (client_id, client_email, project_id, auth_uri, token_uri,
auth_provider_x509_cert_url, client_secret, redirect_uri, javascript_origin)
VALUES
    ('1074499156848-r70ij83ah45s2n2sj1a7n60h61ni7rob.apps.googleusercontent.com',
    'serhii.rubets@gmail.com', 'corporationx', 'https://accounts.google.com/o/oauth2/auth',
    'https://oauth2.googleapis.com/token', 'https://www.googleapis.com/oauth2/v1/certs',
    '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', 'http://localhost:8888/Callback', 'http://localhost:8080');


