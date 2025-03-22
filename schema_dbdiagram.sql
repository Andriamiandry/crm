Table users {
  id int [primary key, increment]
  email varchar(100) [not null, unique]
  password varchar(255)
  hire_date datetime
  created_at timestamp [default: `CURRENT_TIMESTAMP`]
  updated_at timestamp [default: `CURRENT_TIMESTAMP`]
  username varchar(50) [not null, unique]
  status varchar(100)
  token varchar(500)
  is_password_set tinyint(1) [default: 0]
}

Table oauth_users {
  id int [primary key, increment]
  user_id int
  access_token varchar(255) [not null]
  access_token_issued_at datetime [not null]
  access_token_expiration datetime [not null]
  refresh_token varchar(255) [not null]
  refresh_token_issued_at datetime [not null]
  refresh_token_expiration datetime
  granted_scopes varchar(255)
  email varchar(255) [unique]
}

Table user_profile {
  id int [primary key, increment]
  first_name varchar(50)
  last_name varchar(50)
  phone varchar(50)
  department varchar(255)
  salary decimal(10,2)
  status varchar(50)
  oauth_user_image_link varchar(255)
  user_image blob
  bio text
  youtube varchar(255)
  twitter varchar(255)
  facebook varchar(255)
  user_id int
  country varchar(100)
  position varchar(100)
  address varchar(255)
}

Table roles {
  id int [primary key, increment]
  name varchar(255)
}

Table user_roles {
  user_id int [not null]
  role_id int [not null]
}

Table employee {
  id int [primary key, increment]
  username varchar(45) [not null]
  first_name varchar(45) [not null]
  last_name varchar(45) [not null]
  email varchar(45) [not null]
  password varchar(80) [not null]
  provider varchar(45)
}

Table email_template {
  template_id int [primary key, increment]
  name varchar(255) [unique]
  content text
  user_id int
  json_design text
  created_at datetime
}

Table customer_login_info {
  id int [primary key, increment]
  password varchar(255)
  username varchar(255)
  token varchar(500) [unique]
  password_set tinyint(1) [default: 0]
}

Table customer {
  customer_id int [primary key, increment]
  name varchar(255)
  phone varchar(20)
  address varchar(255)
  city varchar(255)
  state varchar(255)
  country varchar(255)
  user_id int
  description text
  position varchar(255)
  twitter varchar(255)
  facebook varchar(255)
  youtube varchar(255)
  created_at datetime
  email varchar(255)
  profile_id int
}

Table trigger_lead {
  lead_id int [primary key, increment]
  customer_id int [not null]
  user_id int
  name varchar(255)
  phone varchar(20)
  employee_id int
  status varchar(50)
  meeting_id varchar(255) [unique]
  google_drive tinyint(1)
  google_drive_folder_id varchar(255)
  created_at datetime
}

Table trigger_ticket {
  ticket_id int [primary key, increment]
  subject varchar(255)
  description text
  status varchar(50)
  priority varchar(50)
  customer_id int [not null]
  manager_id int
  employee_id int
  created_at datetime
}

Table trigger_contract {
  contract_id int [primary key, increment]
  subject varchar(255)
  status varchar(100)
  description text
  start_date date
  end_date date
  amount decimal(10,0)
  google_drive tinyint(1)
  google_drive_folder_id varchar(255)
  lead_id int
  user_id int
  customer_id int
  created_at datetime
}

Table contract_settings {
  id int [primary key, increment]
  amount tinyint(1)
  subject tinyint(1)
  description tinyint(1)
  end_date tinyint(1)
  start_date tinyint(1)
  status tinyint(1)
  user_id int
  status_email_template int
  amount_email_template int
  subject_email_template int
  description_email_template int
  start_email_template int
  end_email_template int
  customer_id int
}

Table lead_action {
  id int [primary key, increment]
  lead_id int [not null]
  action varchar(255)
  date_time datetime
}

Table lead_settings {
  id int [primary key, increment]
  status tinyint(1)
  meeting tinyint(1)
  phone tinyint(1)
  name tinyint(1)
  user_id int
  status_email_template int
  phone_email_template int
  meeting_email_template int
  name_email_template int
  customer_id int
}

Table ticket_settings {
  id int [primary key, increment]
  priority tinyint(1)
  subject tinyint(1)
  description tinyint(1)
  status tinyint(1)
  user_id int
  status_email_template int
  subject_email_template int
  priority_email_template int
  description_email_template int
  customer_id int
}

Table file {
  file_id int [primary key, increment]
  file_name varchar(100)
  file_data blob
  file_type varchar(255)
  lead_id int
  contract_id int
}

Table google_drive_file {
  id int [primary key, increment]
  drive_file_id varchar(255)
  drive_folder_id varchar(255)
  lead_id int
  contract_id int
}

Ref: oauth_users.user_id > users.id
Ref: user_profile.user_id > users.id
Ref: user_roles.user_id > users.id
Ref: user_roles.role_id > roles.id
Ref: email_template.user_id > users.id
Ref: customer.user_id > users.id
Ref: customer.profile_id > customer_login_info.id
Ref: trigger_lead.customer_id > customer.customer_id
Ref: trigger_lead.user_id > users.id
Ref: trigger_lead.employee_id > users.id
Ref: trigger_ticket.customer_id > customer.customer_id
Ref: trigger_ticket.manager_id > users.id
Ref: trigger_ticket.employee_id > users.id
Ref: trigger_contract.lead_id > trigger_lead.lead_id
Ref: trigger_contract.user_id > users.id
Ref: trigger_contract.customer_id > customer.customer_id
Ref: contract_settings.user_id > users.id
Ref: contract_settings.status_email_template > email_template.template_id
Ref: contract_settings.amount_email_template > email_template.template_id
Ref: contract_settings.subject_email_template > email_template.template_id
Ref: contract_settings.description_email_template > email_template.template_id
Ref: contract_settings.start_email_template > email_template.template_id
Ref: contract_settings.end_email_template > email_template.template_id
Ref: contract_settings.customer_id > customer_login_info.id
Ref: lead_action.lead_id > trigger_lead.lead_id
Ref: lead_settings.user_id > users.id
Ref: lead_settings.status_email_template > email_template.template_id
Ref: lead_settings.phone_email_template > email_template.template_id
Ref: lead_settings.meeting_email_template > email_template.template_id
Ref: lead_settings.name_email_template > email_template.template_id
Ref: lead_settings.customer_id > customer_login_info.id
Ref: ticket_settings.user_id > users.id
Ref: ticket_settings.status_email_template > email_template.template_id
Ref: ticket_settings.subject_email_template > email_template.template_id
Ref: ticket_settings.priority_email_template > email_template.template_id
Ref: ticket_settings.description_email_template > email_template.template_id
Ref: ticket_settings.customer_id > customer_login_info.id
Ref: file.lead_id > trigger_lead.lead_id
Ref: file.contract_id > trigger_contract.contract_id
Ref: google_drive_file.lead_id > trigger_lead.lead_id
Ref: google_drive_file.contract_id > trigger_contract.contract_id