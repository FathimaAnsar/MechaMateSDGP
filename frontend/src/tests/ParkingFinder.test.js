import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import ParkingFinder from '../screens/ParkingFinder';
import { BrowserRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import ConnectionManager from '../services/ConnectionManager';

jest.mock('@restart/hooks/cjs/useMediaQuery', () => ({
    __esModule: true,
    default: jest.fn().mockImplementation(() => false),
    useMediaQuery: jest.fn().mockImplementation(() => false),
}));

jest.mock('../services/ConnectionManager');

describe('ParkingFinder Component', () => {
    const mockResponse = {
        places: [{ displayName: { text: 'Parking 1' }, formattedAddress: '123 Main St', location: { latitude: '123', longitude: '456' } }]
    };

    beforeEach(() => {
        ConnectionManager.mockImplementation(() => ({
            getNearbyParking: jest.fn().mockResolvedValue(JSON.stringify(mockResponse))
        }));
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('renders and fetches current location', async () => {
        const mockGetCurrentLocation = jest.fn().mockResolvedValue({ latitude: '123', longitude: '456' });
        render(
            <BrowserRouter>
                <ParkingFinder app={{ getCurrentLocation: mockGetCurrentLocation }} />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(screen.getByTitle('map')).toHaveAttribute('src', expect.stringContaining('123,456'));
        });
    });


    test('navigates user on location retrieval failure', async () => {
        const mockGetCurrentLocation = jest.fn().mockRejectedValue(new Error('Location retrieval failed'));
        render(
            <BrowserRouter>
                <ParkingFinder app={{ getCurrentLocation: mockGetCurrentLocation }} />
            </BrowserRouter>
        );

        await waitFor(() => {
            expect(mockGetCurrentLocation).toHaveBeenCalled();
        });
    });


});
