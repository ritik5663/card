# Card Microservice

A Spring Boot microservice for managing payment cards with RESTful APIs.

## Features

- **Card Management**: Create, read, update, and delete payment cards
- **Card Types**: Support for Credit, Debit, and Prepaid cards
- **Validation**: Comprehensive input validation for card details
- **Security**: Card number masking in responses
- **Search**: Find cards by cardholder name, type, or status
- **Balance Management**: Track and update card balances
- **Status Management**: Activate/deactivate cards
- **Exception Handling**: Global exception handling with proper HTTP status codes

## Technologies

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring Data JPA**
- **H2 Database** (in-memory for development)
- **Maven**
- **Jakarta Validation**

## API Endpoints

### Card Operations

- `POST /api/cards` - Create a new card
- `GET /api/cards` - Get all cards
- `GET /api/cards/{id}` - Get card by ID
- `GET /api/cards/number/{cardNumber}` - Get card by number
- `PUT /api/cards/{id}` - Update card details
- `DELETE /api/cards/{id}` - Delete a card
- `PATCH /api/cards/{id}/deactivate` - Deactivate a card

### Search Operations

- `GET /api/cards/cardholder/{cardholderName}` - Get cards by cardholder name
- `GET /api/cards/type/{cardType}` - Get cards by type (CREDIT/DEBIT/PREPAID)
- `GET /api/cards/active` - Get all active cards
- `GET /api/cards/inactive` - Get all inactive cards
- `GET /api/cards/active/cardholder/{cardholderName}` - Get active cards by cardholder
- `GET /api/cards/active/type/{cardType}` - Get active cards by type

### Utility Operations

- `PATCH /api/cards/{id}/status?isActive=true/false` - Update card status
- `PATCH /api/cards/{id}/balance?newBalance=100.0` - Update card balance
- `GET /api/cards/check/{cardNumber}` - Check if card is active
- `GET /api/cards/count/{cardholderName}` - Count active cards for cardholder

## Sample Request

```json
{
  "cardNumber": "1234567890123456",
  "cardholderName": "John Doe",
  "expiryDate": "12/25",
  "cvv": "123",
  "cardType": "CREDIT",
  "creditLimit": 5000.0,
  "currentBalance": 0.0
}
```

## Running the Application

1. **Build the project**:
   ```bash
   mvn clean install
   ```

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**:
   - API Base URL: `http://localhost:8080/api/cards`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:carddb`
     - Username: `sa`
     - Password: `password`

## Testing

Run the integration tests:
```bash
mvn test
```

## Card Validation Rules

- **Card Number**: Exactly 16 digits
- **Cardholder Name**: 2-100 characters
- **Expiry Date**: MM/YY format
- **CVV**: 3-4 digits
- **Card Type**: CREDIT, DEBIT, or PREPAID

## Error Responses

The API returns standardized error responses:

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Card with number 1234567890123456 already exists",
  "path": "/api/cards"
}
```

## Security Notes

- Card numbers are masked in API responses (****-****-****-3456)
- CVV is stored but not exposed in responses
- Input validation prevents malformed data
- H2 database is for development only (replace with production database in production)

## Database Schema

The `cards` table includes:
- `id` (Primary Key)
- `card_number` (Unique, 16 digits)
- `cardholder_name`
- `expiry_date`
- `cvv`
- `card_type`
- `is_active`
- `credit_limit`
- `current_balance`
- `created_at`
- `updated_at`
