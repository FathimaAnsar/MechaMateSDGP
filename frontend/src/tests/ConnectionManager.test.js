import ConnectionManager from '../services/ConnectionManager';
import fetchMock from 'jest-fetch-mock';
fetchMock.enableMocks();

describe('ConnectionManager', () => {
    let connectionManager;

    beforeEach(() => {
        connectionManager = new ConnectionManager();
        fetch.resetMocks();
    });

    test('signin should handle a successful request', async () => {
        const mockResponse = { token: 'dummyToken' };
        fetch.mockResponseOnce(JSON.stringify(mockResponse));

        const response = await connectionManager.signin('user', 'pass');
        expect(JSON.parse(response)).toEqual(mockResponse);
        expect(fetch).toHaveBeenCalledWith(
            expect.stringContaining('/api/v1/auth/signin'),
            expect.objectContaining({
                method: 'POST',
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: expect.any(URLSearchParams),
                credentials: 'include',
            })
        );
    });

    test('getUserProfile should handle a successful request', async () => {
        const mockResponse = { profile: 'dummyProfile' };
        fetch.mockResponseOnce(JSON.stringify(mockResponse));

        const response = await connectionManager.getUserProfile();
        expect(JSON.parse(response)).toEqual(mockResponse);
        expect(fetch).toHaveBeenCalledWith(
            expect.stringContaining('/api/v1/general/profile'),
            expect.objectContaining({
                method: 'GET',
                credentials: 'include',
            })
        );
    });

    test('getNearbyParking should handle a successful request', async () => {
        const mockResponse = [{ id: 'park1', name: 'Parking 1' }];
        fetch.mockResponseOnce(JSON.stringify(mockResponse));

        const response = await connectionManager.getNearbyParking('35.6895', '139.6917', '500', '5');
        expect(JSON.parse(response)).toEqual(mockResponse);
        expect(fetch).toHaveBeenCalledWith(
            expect.stringContaining('/api/v1/features/get-nearby-parking'),
            expect.objectContaining({
                method: 'GET',
                credentials: 'include',
            })
        );
    });

});
