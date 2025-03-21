// You may use this file to create any models

export interface Menu {
  id: string;
  name: string;
  description: string;
  price: number;
  quantity: number;
}

export interface Cart {
  menus: Menu[]
}

export interface LineItem {
  id: string;
  price: number;
  quantity: number
}

// export interface Customer {
//   username: string;
//   password: string;
// }

export interface Order {
  username: string;
  password: string;
  items: LineItem[]
}

export interface Confirmation {
  orderId: string;
  paymentId: string;
  total: number;
  timestamp: string;
}