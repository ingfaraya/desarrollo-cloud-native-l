export interface Alert {
    id: number;
    description: string;
    priority: string;
    createdAt: string; // Se maneja como string porque viene en formato ISO 8601
    patientId: number;
  }
  
  export interface PaginatedResponse {
    content: Alert[];
    pageable: {
      pageNumber: number;
      pageSize: number;
    };
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    first: boolean;
    last: boolean;
    numberOfElements: number;
  }
  