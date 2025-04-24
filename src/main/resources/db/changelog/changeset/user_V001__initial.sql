-- Ensure pgcrypto is enabled
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE country (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    name                   VARCHAR(64)            UNIQUE NOT NULL,
    created_at             TIMESTAMPTZ            DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ            DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT
);

CREATE TABLE users (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    name                   VARCHAR(64)            NOT NULL,
    surname                VARCHAR(64)            NOT NULL,
    username               VARCHAR(64)            UNIQUE NOT NULL,
    password               VARCHAR(64)            NOT NULL,
    email                  VARCHAR(64)            UNIQUE NOT NULL,
    phone                  VARCHAR(15)            UNIQUE,
    about_me               VARCHAR(4096),
    active                 boolean                DEFAULT true NOT NULL,
    city                   VARCHAR(32),
    country_id             UUID                   NOT NULL,
    experience             INT,
    pic_url                TEXT,
    uploaded_pic_url       TEXT,
    created_at             TIMESTAMPTZ            DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ            DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

   CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE TABLE subscription (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    follower_id            UUID NOT NULL,
    followee_id            UUID NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_follower_id FOREIGN KEY (follower_id) REFERENCES users (id),
    CONSTRAINT fk_followee_id FOREIGN KEY (followee_id) REFERENCES users (id)
);

CREATE TABLE mentorship (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    mentor_id              UUID NOT NULL,
    mentee_id              UUID NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_mentor_id FOREIGN KEY (mentor_id) REFERENCES users (id),
    CONSTRAINT fk_mentee_id FOREIGN KEY (mentee_id) REFERENCES users (id)
);

CREATE TABLE mentorship_request (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    description            VARCHAR(4096)          NOT NULL,
    requester_id           UUID                   NOT NULL,
    receiver_id            UUID                   NOT NULL,
    status                 SMALLINT               DEFAULT 0 NOT NULL,
    rejection_reason       VARCHAR(4096),
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_mentee_req_id FOREIGN KEY (requester_id) REFERENCES users (id),
    CONSTRAINT fk_mentor_req_id FOREIGN KEY (receiver_id) REFERENCES users (id)
);

CREATE TABLE skill (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    title                  VARCHAR(64)            UNIQUE NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT
);

CREATE TABLE user_skill (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    skill_id               UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_user_skill_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_skill_user_id FOREIGN KEY (skill_id) REFERENCES skill (id)
);

CREATE TABLE user_skill_guarantee (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    skill_id               UUID                   NOT NULL,
    guarantor_id           UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

  CONSTRAINT fk_user_skill_guarantee_user FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_user_skill_guarantee_skill FOREIGN KEY (skill_id) REFERENCES skill (id),
  CONSTRAINT fk_user_skill_guarantee_guarantor FOREIGN KEY (guarantor_id) REFERENCES users (id)
);

CREATE TABLE recommendation (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    content                VARCHAR(4096)          NOT NULL,
    author_id              UUID                   NOT NULL,
    receiver_id            UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_recommender_id FOREIGN KEY (author_id) REFERENCES users (id),
    CONSTRAINT fk_receiver_id FOREIGN KEY (receiver_id) REFERENCES users (id)
);

CREATE TABLE skill_offer (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    skill_id               UUID                   NOT NULL,
    recommendation_id      UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_skill_offered_id FOREIGN KEY (skill_id) REFERENCES skill (id) ON DELETE CASCADE,
    CONSTRAINT fk_recommendation_skill_id FOREIGN KEY (recommendation_id) REFERENCES recommendation (id) ON DELETE CASCADE
);

CREATE TABLE recommendation_request (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    message                VARCHAR(4096)          NOT NULL,
    requester_id           UUID                   NOT NULL,
    receiver_id            UUID                   NOT NULL,
    status                 SMALLINT               DEFAULT 0 NOT NULL,
    rejection_reason       VARCHAR(4096),
    recommendation_id      UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_requester_recommendation_id FOREIGN KEY (requester_id) REFERENCES users (id),
    CONSTRAINT fk_receiver_recommendation_id FOREIGN KEY (receiver_id) REFERENCES users (id),
    CONSTRAINT fk_recommendation_req_id FOREIGN KEY (recommendation_id) REFERENCES recommendation (id) ON DELETE CASCADE
);

CREATE TABLE skill_request (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    request_id             UUID                   NOT NULL,
    skill_id               UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_request_skill_id FOREIGN KEY (request_id) REFERENCES recommendation_request (id) ON DELETE CASCADE,
    CONSTRAINT fk_skill_request_id FOREIGN KEY (skill_id) REFERENCES skill (id)
);

CREATE TABLE contact (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    contact                VARCHAR(128)           NOT NULL UNIQUE,
    type                   SMALLINT               NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_contact_owner_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE project_subscription (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id             UUID                   NOT NULL,
    follower_id            UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_project_follower_id FOREIGN KEY (follower_id) REFERENCES users (id)
);

CREATE TABLE event (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    title                  VARCHAR(64)            NOT NULL,
    description            VARCHAR(4096)          NOT NULL,
    start_date             TIMESTAMPTZ            DEFAULT current_timestamp,
    end_date               TIMESTAMPTZ            DEFAULT current_timestamp,
    location               VARCHAR(128)           NOT NULL,
    max_attendees          BIGINT,
    user_id                UUID                   NOT NULL,
    type                   SMALLINT               NOT NULL,
    status                 SMALLINT               NOT NULL DEFAULT 0,
    created_at             TIMESTAMPTZ            DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ            DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_event_owner_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE event_skill (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id               UUID                   NOT NULL,
    skill_id               UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_event_skill_id FOREIGN KEY (event_id) REFERENCES event (id) ON DELETE CASCADE,
    CONSTRAINT fk_skill_event_id FOREIGN KEY (skill_id) REFERENCES skill (id)
);

CREATE TABLE user_event (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    event_id               UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_user_event_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_event_user_id FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE rating (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    event_id               UUID                   NOT NULL,
    rate                   SMALLINT               CHECK (rate BETWEEN 1 AND 5),
    comment                VARCHAR(4096),
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_rater_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_event_rated_id FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE contact_preferences (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    preference             SMALLINT               NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_contact_preferences_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE goal (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    title                  VARCHAR(64)            NOT NULL,
    description            VARCHAR(4096)          NOT NULL,
    parent_goal_id         UUID                   NOT NULL,
    status                 SMALLINT               DEFAULT 0 NOT NULL,
    deadline               TIMESTAMPTZ             DEFAULT current_timestamp,
    mentor_id              UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_goal_id FOREIGN KEY (parent_goal_id) REFERENCES goal (id),
    CONSTRAINT fk_mentor_id FOREIGN KEY (mentor_id) REFERENCES users (id)
);

CREATE TABLE goal_invitation (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    goal_id                UUID                   NOT NULL,
    inviter_id             UUID                   NOT NULL,
    invited_id             UUID                   NOT NULL,
    status                 SMALLINT               DEFAULT 0 NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_inviter_id FOREIGN KEY (inviter_id) REFERENCES users (id),
    CONSTRAINT fk_invited_id FOREIGN KEY (invited_id) REFERENCES users (id),
    CONSTRAINT fk_goal_id FOREIGN KEY (goal_id) REFERENCES goal (id)
);

CREATE TABLE user_goal (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    goal_id                UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,


   CONSTRAINT fk_user_goal_id FOREIGN KEY (user_id) REFERENCES users (id),
   CONSTRAINT fk_goal_user_id FOREIGN KEY (goal_id) REFERENCES goal (id)
);

CREATE TABLE goal_skill (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    goal_id                UUID                   NOT NULL,
    skill_id               UUID                   NOT NULL,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

   CONSTRAINT fk_goal_skill_id FOREIGN KEY (goal_id) REFERENCES goal (id),
   CONSTRAINT fk_skill_goal_id FOREIGN KEY (skill_id) REFERENCES skill (id)
);

CREATE TABLE user_premium (
    id                     UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id                UUID                   NOT NULL,
    start_date             TIMESTAMPTZ             DEFAULT current_timestamp,
    end_date               TIMESTAMPTZ             DEFAULT current_timestamp,
    created_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    updated_at             TIMESTAMPTZ             DEFAULT current_timestamp,
    created_by             VARCHAR(64),
    updated_by             VARCHAR(64),
    version                BIGINT,

    CONSTRAINT fk_user_premium_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE google_credentials (
    id                          UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id                   VARCHAR(128)           UNIQUE NOT NULL,
    client_email                VARCHAR(128)           UNIQUE NOT NULL,
    project_id                  VARCHAR(32)            UNIQUE NOT NULL,
    auth_uri                    VARCHAR(64)            UNIQUE NOT NULL,
    token_uri                   VARCHAR(64)            UNIQUE NOT NULL,
    auth_provider_x509_cert_url VARCHAR(64)            UNIQUE NOT NULL,
    client_secret               VARCHAR(64)            UNIQUE NOT NULL,
    redirect_uri                VARCHAR(64)            UNIQUE NOT NULL,
    javascript_origin           VARCHAR(64)            UNIQUE NOT NULL
);

INSERT INTO google_credentials (client_id, client_email, project_id, auth_uri, token_uri,
auth_provider_x509_cert_url, client_secret, redirect_uri, javascript_origin)
VALUES
    ('1074499156848-r70ij83ah45s2n2sj1a7n60h61ni7rob.apps.googleusercontent.com',
    'serhii.rubets@gmail.com', 'corporationx', 'https://accounts.google.com/o/oauth2/auth',
    'https://oauth2.googleapis.com/token', 'https://www.googleapis.com/oauth2/v1/certs',
    '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', 'http://localhost:8888/Callback', 'http://localhost:8080');
