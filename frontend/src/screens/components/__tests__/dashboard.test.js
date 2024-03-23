// Import necessary libraries and dependencies for testing
import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import Dashboard from './Dashboard';

// Mock the props required for the component
const mockApp = {
  setVehicleList: jest.fn(),
  changePage: jest.fn(),
  getUserProfile: jest.fn(() => ({ firstName: 'John' })),
};

// Mock the vehicle data
const mockVehicles = [{ id: 1, name: 'Vehicle 1' }, { id: 2, name: 'Vehicle 2' }];

// Mock the ConnectionManager and its methods
jest.mock('../services/ConnectionManager', () => ({
  __esModule: true,
  default: jest.fn().mockImplementation(() => ({
    getVehicleList: jest.fn().mockResolvedValue(JSON.stringify(mockVehicles)),
  })),
}));

describe('Dashboard component', () => {
  // Test rendering of greeting with user name
  it('renders greeting with user name', () => {
    const { getByText } = render(<Dashboard app={mockApp} />);
    expect(getByText('Good morning, John!')).toBeInTheDocument();
  });

  // Test rendering of loading spinner initially
  it('renders loading spinner initially', () => {
    const { getByTestId } = render(<Dashboard app={mockApp} />);
    expect(getByTestId('spinner')).toBeInTheDocument();
  });

  // Test rendering of vehicles after loading
  it('renders vehicles after loading', async () => {
    const { getByText } = render(<Dashboard app={mockApp} />);
    await waitFor(() => expect(mockApp.setVehicleList).toHaveBeenCalledWith(mockVehicles));
    expect(getByText('Vehicle 1')).toBeInTheDocument();
    expect(getByText('Vehicle 2')).toBeInTheDocument();
  });

  // Test navigation to ViewVehicle page on card click
  it('navigates to ViewVehicle page on card click', async () => {
    const { getByText } = render(<Dashboard app={mockApp} />);
    await waitFor(() => expect(mockApp.setVehicleList).toHaveBeenCalledWith(mockVehicles));
    fireEvent.click(getByText('Vehicle 1'));
    expect(mockApp.changePage).toHaveBeenCalledWith('view_vehicle_page');
  });

  // Test navigation to MyVehiclesUI page on "Add a Vehicle" button click
  it('navigates to MyVehiclesUI page on "Add a Vehicle" button click', async () => {
    const { getByText } = render(<Dashboard app={mockApp} />);
    await waitFor(() => expect(mockApp.setVehicleList).toHaveBeenCalledWith(mockVehicles));
    fireEvent.click(getByText('Add a Vehicle'));
    expect(mockApp.changePage).toHaveBeenCalledWith('my_vehicles_page');
  });
});
