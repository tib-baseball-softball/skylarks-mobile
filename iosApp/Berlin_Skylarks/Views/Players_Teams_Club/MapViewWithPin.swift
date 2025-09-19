//
//  MapViewWithPin.swift
//  Berlin Skylarks
//
//  Created by David Battefeld on 05.08.22.
//

import MapKit
import SwiftUI

struct MapViewWithPin: View {

    #if os(iOS)
        @Environment(\.verticalSizeClass) var verticalSizeClass

        //computed property to use a bigger map on larger screens, but not on portrait iPhones
        private var expandMap: Bool {
            if verticalSizeClass == .regular
                && UIDevice.current.userInterfaceIdiom != .phone
            {
                return true
            }
            return false
        }
    #endif

    var latitude: Double
    var longitude: Double
    var name: String

    var body: some View {
        let fieldPin = [
            Ballpark(
                name: name,
                coordinate: CLLocationCoordinate2D(
                    latitude: latitude,
                    longitude: longitude
                )
            )
        ]
        let position = MapCameraPosition.region(
            MKCoordinateRegion(
                center: CLLocationCoordinate2D(
                    latitude: latitude,
                    longitude: longitude
                ),
                span: MKCoordinateSpan(
                    latitudeDelta: 0.015,
                    longitudeDelta: 0.015
                )
            )
        )
        Map(initialPosition: position, interactionModes: []) {
            ForEach(fieldPin) { pin in
                Annotation(pin.name, coordinate: pin.coordinate) {
                    Image(systemName: "baseball")
                        .font(.headline)
                        .background(Color.skylarksRed)
                        .foregroundStyle(.white)
                        .clipShape(.circle)
                }
            }
        }
        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
        #if os(iOS)
            .frame(height: expandMap ? 500 : 200)
        #else
            // macOS
            .frame(height: 500)
        #endif

        Button("Open in Maps") {
            if !fieldPin.isEmpty {
                let coordinate = fieldPin.first!.coordinate
                let location = CLLocation(latitude: coordinate.latitude, longitude: coordinate.longitude)
                let mapItem = MKMapItem(location: location, address: nil)
                mapItem.name = fieldPin.first!.name
                mapItem.openInMaps()
            }
        }
        .buttonStyle(.glass)
    }
}

#Preview {
    List {
        MapViewWithPin(latitude: 15, longitude: 35, name: "Important Location")
    }
}
